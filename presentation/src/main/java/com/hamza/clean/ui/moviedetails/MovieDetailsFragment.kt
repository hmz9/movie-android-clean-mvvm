package com.hamza.clean.ui.moviedetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hamza.clean.R
import com.hamza.clean.databinding.FragmentMovieDetailsBinding
import com.hamza.clean.ui.base.BaseFragment
import com.hamza.clean.util.launchAndRepeatWithViewLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author by Ameer Hamza on 03/09/2023
 */

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModel.provideFactory(factory, args.movieId)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() = with(binding) {
        favorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun observeViewModel() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            uiState.collect { handleMovieDetailsUiState(it) }
        }
    }

    private fun handleMovieDetailsUiState(movieState: MovieDetailsViewModel.MovieDetailsUiState) {
        binding.movieTitle.text = movieState.title
        binding.description.text = movieState.description
        binding.movieReleaseDate.text = movieState.releaseDate
        loadImage("https://image.tmdb.org/t/p/w200${movieState.imageUrl}")
        updateFavoriteDrawable(getFavoriteDrawable(movieState.isFavorite))
    }

    private fun getFavoriteDrawable(favorite: Boolean): Drawable? = if (favorite) {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_fill_white_48)
    } else {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border_white_48)
    }

    private fun updateFavoriteDrawable(drawable: Drawable?) = with(binding.favorite) {
        setImageDrawable(drawable)
    }

    private fun loadImage(url: String) =
        Glide
            .with(this)
            .asDrawable()
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .thumbnail(getThumbnailRequest(binding.image, url))
            .load(url)
            .placeholder(R.color.light_gray)
            .error(R.drawable.bg_image)
            .into(binding.image)

    private fun getThumbnailRequest(imageView: ImageView, url: String): RequestBuilder<Drawable> = Glide.with(imageView)
        .asDrawable()
        .format(DecodeFormat.PREFER_RGB_565)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .sizeMultiplier(0.2F)
        .load(url)
}