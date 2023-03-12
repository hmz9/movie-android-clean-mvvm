package com.hamza.data.mapper

import com.hamza.data.entities.MovieDbData
import com.hamza.domain.entities.MovieEntity

/**
 * Created by Ameer Hamza on 03/09/2023
 **/

fun MovieEntity.toDbData() = MovieDbData(
    id = id,
    image = image,
    description = description,
    title = title,
    releaseDate = releaseDate,
    isFavorite = isFavorite
)
