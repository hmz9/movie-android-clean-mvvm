package com.hamza.clean.ui.adapter.movie.viewholder

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hamza.clean.R
import com.hamza.clean.databinding.ItemMovieBinding
import com.hamza.clean.entities.MovieListItem
import com.hamza.clean.util.hide
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hamza.clean.ui.adapter.movie.MoviePagingAdapter
import com.hamza.clean.util.show
import com.hamza.data.entities.MovieData
import java.text.FieldPosition

/**
 * Created by Ameer Hamza on 03/09/2023
 */
class MovieViewHolder(
    parent: ViewGroup,
    private val onMovieClick: (movieId: Int) -> Unit,
    private val imageFixedSize: Int
) : RecyclerView.ViewHolder(
    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    private val binding = ItemMovieBinding.bind(itemView)

    fun bind(movie: MovieListItem.Movie) = with(binding) {

        loadImage(image, "https://image.tmdb.org/t/p/w92${movie.imageUrl}")
        setTextIfNotNull(movie.title, movieTitle)
        setTextIfNotNull(movie.releaseDate, movieReleaseDate)


        root.setOnClickListener { onMovieClick(movie.id) }
    }

    private fun setTextIfNotNull(text: String?, textView: TextView) {
        text?.also { title ->
            textView.show()
            textView.text = title
        } ?: kotlin.run {
            textView.hide()
        }
    }

    fun unBind() = with(binding) {
        clearImage(image)
        root.setOnClickListener(null)
    }

    private fun loadImage(image: AppCompatImageView, url: String) = Glide.with(image)
        .asDrawable()
        .override(imageFixedSize)
        .format(DecodeFormat.PREFER_RGB_565)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .thumbnail(getThumbnailRequest(image, url))
        .load(url)
        .placeholder(R.color.light_gray)
        .error(R.drawable.bg_image)
        .into(image)

    private fun getThumbnailRequest(imageView: ImageView, url: String): RequestBuilder<Drawable> = Glide.with(imageView)
        .asDrawable()
        .override(imageFixedSize)
        .format(DecodeFormat.PREFER_RGB_565)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .sizeMultiplier(0.2F)
        .load(url)

    private fun clearImage(image: AppCompatImageView) = Glide.with(image).clear(image)
}