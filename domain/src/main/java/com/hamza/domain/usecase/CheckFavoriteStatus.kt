package com.hamza.domain.usecase

import com.hamza.domain.repository.MovieRepository
import com.hamza.domain.util.Result

/**
 * @author by Ameer Hamza on 03/09/2023
 */
class CheckFavoriteStatus(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<Boolean> = movieRepository.checkFavoriteStatus(movieId)
}