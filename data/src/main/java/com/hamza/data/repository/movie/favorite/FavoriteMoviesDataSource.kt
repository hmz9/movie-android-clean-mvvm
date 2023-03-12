package com.hamza.data.repository.movie.favorite

import androidx.paging.PagingSource
import com.hamza.data.entities.MovieDbData
import com.hamza.domain.util.Result

/**
 * @author by Ameer Hamza on 03/09/2023
 */
interface FavoriteMoviesDataSource {

    interface Local {
        fun favoriteMovies(): PagingSource<Int, MovieDbData>
        suspend fun getFavoriteMovieIds(): Result<List<Int>>
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
        suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    }

}