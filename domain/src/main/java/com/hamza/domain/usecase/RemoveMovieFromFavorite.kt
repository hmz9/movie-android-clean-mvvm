package com.hamza.domain.usecase

import com.hamza.domain.repository.MovieRepository

/**
 * @author by Ameer Hamza on 03/09/2023
 */
class RemoveMovieFromFavorite(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = movieRepository.removeMovieFromFavorite(movieId)
}