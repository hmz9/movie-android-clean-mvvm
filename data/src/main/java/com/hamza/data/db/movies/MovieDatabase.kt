package com.hamza.data.db.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamza.data.db.favoritemovies.FavoriteMovieDao
import com.hamza.data.entities.FavoriteMovieDbData
import com.hamza.data.entities.MovieDbData
import com.hamza.data.entities.MovieRemoteKeyDbData

/**
 * Created by Ameer Hamza on 03/09/2023
 */
@Database(
    entities = [MovieDbData::class, FavoriteMovieDbData::class, MovieRemoteKeyDbData::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}