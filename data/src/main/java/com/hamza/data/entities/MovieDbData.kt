package com.hamza.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hamza.domain.entities.MovieEntity

/**
 * Created by Ameer Hamza on 03/09/2023
 */
@Entity(tableName = "movies")
data class MovieDbData(
    @PrimaryKey val id: Int,
    val description: String,
    val image: String,
    val title: String,
    val releaseDate: String,
    val isFavorite: Boolean
)

fun MovieDbData.toDomain() = MovieEntity(
    id = id,
    image = image,
    description = description,
    title = title,
    releaseDate = releaseDate,
    isFavorite = isFavorite
)