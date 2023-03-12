package com.hamza.data.entities

import com.hamza.domain.entities.MovieEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by Ameer Hamza on 03/09/2023
 */

data class MovieDataResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val moviesList: List<MovieData>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)
data class MovieData(
    @SerializedName("id") val id: Int?,
    @SerializedName("overview") val description: String?,
    @SerializedName("poster_path") val image: String?,
    @SerializedName("original_title") val title: String?,
    @SerializedName("release_date") val releaseDate: String?,
)

fun MovieData.toDomain() = MovieEntity(
    id = id?: 0,
    image = image ?: "",
    description = description ?: "",
    title = title ?: "",
    releaseDate = releaseDate ?: "",
    isFavorite = false
)