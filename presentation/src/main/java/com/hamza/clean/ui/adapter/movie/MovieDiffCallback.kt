package com.hamza.clean.ui.adapter.movie

import androidx.recyclerview.widget.DiffUtil
import com.hamza.clean.entities.MovieListItem

/**
 * @author by Ameer Hamza on 03/09/2023
 */
object MovieDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {

    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return (oldItem as MovieListItem.Movie).id == (newItem as MovieListItem.Movie).id
    }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean = oldItem == newItem
}