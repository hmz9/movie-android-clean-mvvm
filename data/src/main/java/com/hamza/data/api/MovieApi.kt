package com.hamza.data.api

import com.hamza.data.BuildConfig
import com.hamza.data.entities.MovieData
import com.hamza.data.entities.MovieDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ameer Hamza on 03/09/2023
 */
interface MovieApi {

    @GET("/3/movie/popular?api_key=${BuildConfig.TMBD_API_KEY}")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): MovieDataResponse

    @GET("/movies")
    suspend fun getMovies(@Query("id") movieIds: List<Int>): List<MovieData>

    @GET("3/movie/{movie_id}?api_key=${BuildConfig.TMBD_API_KEY}")
    suspend fun getMovie(@Path("movie_id") movieId: Int): MovieData

    @GET("3/search/movie?api_key=e5ea3092880f4f3c25fbc537e9b37dc1")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): MovieDataResponse
}