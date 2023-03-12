package com.hamza.domain.entities

/**
 * Created by Ameer Hamza on 03/09/2023
 */
data class MovieEntity(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val releaseDate: String,
    var isFavorite: Boolean,
)