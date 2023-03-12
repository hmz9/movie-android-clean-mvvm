package com.hamza.clean.ui.feed

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hamza.clean.entities.MovieListItem
import com.hamza.clean.ui.base.BaseViewModel
import com.hamza.clean.ui.feed.usecase.GetMoviesWithSeparators
import com.hamza.clean.ui.moviedetails.MovieDetailsViewModel
import com.hamza.clean.util.singleSharedFlow
import com.hamza.data.util.DispatchersProvider
import com.hamza.domain.usecase.AddMovieToFavorite
import com.hamza.domain.usecase.CheckFavoriteStatus
import com.hamza.domain.usecase.GetMovieDetails
import com.hamza.domain.usecase.RemoveMovieFromFavorite
import com.hamza.domain.util.getResult
import com.hamza.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Ameer Hamza on 03/09/2023
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val addMovieToFavorite: AddMovieToFavorite,
    private val removeMovieFromFavorite: RemoveMovieFromFavorite,
    getMoviesWithSeparators: GetMoviesWithSeparators,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FeedUiState(
        val showLoading: Boolean = true,
        val errorMessage: String? = null,
        val isFavorite: Boolean = false,
        val movieId: Int? = null
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    val movies: Flow<PagingData<MovieListItem>> = getMoviesWithSeparators.movies(
        pageSize = 20
    ).cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = singleSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    fun onMovieClicked(movieId: Int) =
        _navigationState.tryEmit(NavigationState.MovieDetails(movieId))

    fun onLoadStateUpdate(loadState: CombinedLoadStates) {
        val showLoading = loadState.refresh is LoadState.Loading

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiState.update { it.copy(showLoading = showLoading, errorMessage = error) }
    }
}