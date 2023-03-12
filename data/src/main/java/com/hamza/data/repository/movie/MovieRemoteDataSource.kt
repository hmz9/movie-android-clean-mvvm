package com.hamza.data.repository.movie

import com.hamza.data.api.MovieApi
import com.hamza.data.entities.toDomain
import com.hamza.domain.entities.MovieEntity
import com.hamza.domain.util.Result

/**
 * Created by Ameer Hamza on 03/09/2023
 */
class MovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieEntity>> = try {
        val result = movieApi.getMovies(page, limit)
        Result.Success(result.moviesList?.map { it.toDomain() } ?: emptyList())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = try {
        val result = movieApi.getMovie(movieId)
        Result.Success(result.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieEntity>> = try {
        val result = movieApi.search(query, page, limit)
        Result.Success(result.moviesList?.map { it.toDomain() } ?: emptyList())
    } catch (e: Exception) {
        Result.Error(e)
    }
}