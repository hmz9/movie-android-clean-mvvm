package com.hamza.clean.entities

/**
 * @author by Ameer Hamza on 03/09/2023
 */
sealed class MovieListItem {
    data class Movie(
        val id: Int,
        val imageUrl: String,
        val title: String? = null,
        val releaseDate: String? = null,
        var isFavorite: Boolean = false
    ) : MovieListItem()

    data class Separator(val category: String) : MovieListItem()
}
