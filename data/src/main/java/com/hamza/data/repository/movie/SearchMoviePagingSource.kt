package com.hamza.data.repository.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hamza.domain.entities.MovieEntity
import com.hamza.domain.util.getResult

private const val STARTING_PAGE_INDEX = 1

/**
 * @author by Ameer Hamza on 03/09/2023
 */
class SearchMoviePagingSource(
    private val query: String,
    private val remote: MovieDataSource.Remote
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return remote.search(query, page, params.loadSize).getResult({
            LoadResult.Page(
                data = it.data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (it.data.isEmpty()) null else page + 1
            )
        }, {
            LoadResult.Error(it.error)
        })
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}