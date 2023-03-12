package com.hamza.domain.usecase

import com.hamza.domain.entities.MovieEntity
import com.hamza.domain.repository.MovieRepository
import com.hamza.domain.util.Result

/**
 * Created by Ameer Hamza on 03/09/2023
 **/
class GetMovieDetails(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieEntity> = movieRepository.getMovie(movieId)
}
