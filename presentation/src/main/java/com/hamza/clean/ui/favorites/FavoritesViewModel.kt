package com.hamza.clean.ui.favorites

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.hamza.clean.entities.MovieListItem
import com.hamza.clean.mapper.toPresentation
import com.hamza.clean.ui.base.BaseViewModel
import com.hamza.clean.util.singleSharedFlow
import com.hamza.data.util.DispatchersProvider
import com.hamza.domain.usecase.AddMovieToFavorite
import com.hamza.domain.usecase.CheckFavoriteStatus
import com.hamza.domain.usecase.GetFavoriteMovies
import com.hamza.domain.usecase.RemoveMovieFromFavorite
import com.hamza.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author by Ameer Hamza on 03/09/2023
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val addMovieToFavorite: AddMovieToFavorite,
    private val removeMovieFromFavorite: RemoveMovieFromFavorite,
    getFavoriteMovies: GetFavoriteMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteUiState(
        val isLoading: Boolean = true,
        val noDataAvailable: Boolean = false,
        val isFavorite: Boolean = false
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    val movies: Flow<PagingData<MovieListItem>> = getFavoriteMovies(30).map {
        it.map { it.toPresentation() as MovieListItem }
    }.cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<FavoriteUiState> = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = singleSharedFlow()
    val navigationState = _navigationState.asSharedFlow()


    fun onMovieClicked(movieId: Int) =
        _navigationState.tryEmit(NavigationState.MovieDetails(movieId))

    fun onLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        _uiState.update {
            it.copy(
                isLoading = showLoading,
                noDataAvailable = showNoData
            )
        }
    }
}