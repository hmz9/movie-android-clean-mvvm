package com.hamza.data.repository.movie

import androidx.paging.*
import com.hamza.data.entities.toDomain
import com.hamza.data.exception.DataNotAvailableException
import com.hamza.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.hamza.domain.entities.MovieEntity
import com.hamza.domain.repository.MovieRepository
import com.hamza.domain.util.Result
import com.hamza.domain.util.getResult
import com.hamza.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

/**
 * Created by Ameer Hamza on 03/09/2023
 */
class MovieRepositoryImpl constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val remoteMediator: MovieRemoteMediator,
    private val localFavorite: FavoriteMoviesDataSource.Local
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun movies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false,
        ),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { local.movies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ), pagingSourceFactory = { localFavorite.favoriteMovies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchMoviePagingSource(query, remote) }
    ).flow

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = local.getMovie(movieId).getResult({
        it
    }, {
        remote.getMovie(movieId)
    })

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = localFavorite.checkFavoriteStatus(movieId)

    override suspend fun addMovieToFavorite(movieId: Int) {
        getMovie(movieId).onSuccess {
            local.saveMovies(Collections.singletonList(it))
            localFavorite.addMovieToFavorite(movieId)
        }
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) = localFavorite.removeMovieFromFavorite(movieId)


}