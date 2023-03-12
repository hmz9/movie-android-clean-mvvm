package com.hamza.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author by Ameer Hamza on 03/09/2023
 */
@Entity(tableName = "favorite_movies")
data class FavoriteMovieDbData(
    @PrimaryKey val movieId: Int
)