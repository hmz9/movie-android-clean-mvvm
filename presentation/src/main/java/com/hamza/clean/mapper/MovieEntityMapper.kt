package com.hamza.clean.mapper

import com.hamza.clean.entities.MovieListItem
import com.hamza.domain.entities.MovieEntity

/**
 * @author by Ameer Hamza on 03/09/2023
 */

fun MovieEntity.toPresentation() = MovieListItem.Movie(
    id = id,
    imageUrl = image,
    title = title,
    releaseDate = releaseDate,
    isFavorite = isFavorite
)