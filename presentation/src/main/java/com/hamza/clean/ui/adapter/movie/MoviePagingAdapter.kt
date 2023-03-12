package com.hamza.clean.ui.adapter.movie

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hamza.clean.R
import com.hamza.clean.entities.MovieListItem
import com.hamza.clean.ui.adapter.movie.viewholder.MovieViewHolder
import com.hamza.clean.ui.adapter.movie.viewholder.SeparatorViewHolder

/**
 * Created by Ameer Hamza on 03/09/2023
 */
class MoviePagingAdapter(
    private val onMovieClick: (movieId: Int) -> Unit,
    private val imageFixedSize: Int,
) : PagingDataAdapter<MovieListItem, ViewHolder>(MovieDiffCallback) {

    override fun getItemViewType(position: Int): Int = when (peek(position)) {
        is MovieListItem.Movie -> R.layout.item_movie
        is MovieListItem.Separator -> R.layout.item_separator
        null -> throw RuntimeException("Unknown view type")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
        R.layout.item_movie -> MovieViewHolder(parent, onMovieClick ,imageFixedSize)
        R.layout.item_separator -> SeparatorViewHolder(parent)
        else -> throw RuntimeException("Illegal view type")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MovieViewHolder -> holder.bind(item as MovieListItem.Movie)
            is SeparatorViewHolder -> holder.bind(item as MovieListItem.Separator)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        when (holder) {
            is MovieViewHolder -> holder.unBind()
        }
    }
}