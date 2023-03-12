package com.hamza.data.db.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamza.data.entities.MovieDbData

/**
 * Created by Ameer Hamza on 03/09/2023
 */
@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY id")
    fun movies(): PagingSource<Int, MovieDbData>

    /**
     * Get all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movies ORDER BY id")
    fun getMovies(): List<MovieDbData>

    /**
     * Get movie by id.
     * **/
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): MovieDbData?

    /**
     * Insert all movies.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieDbData>)

    /**
     * Delete all movies except favorites.
     */
    @Query("DELETE FROM movies WHERE id NOT IN (SELECT movieId FROM favorite_movies)")
    suspend fun clearMoviesExceptFavorites()
}