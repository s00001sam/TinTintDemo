package com.sam.tintintdemo.source.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sam.tintintdemo.data.GalleryData
import retrofit2.HttpException
import java.io.IOException

class GalleryPagingSource(
    private val datum: List<GalleryData>,
) : PagingSource<Int, GalleryData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryData> {
        return try {
            val key = params.key ?: 0
            val startIndex = key * params.loadSize
            val endIndex = minOf(startIndex + params.loadSize, datum.size)

            return LoadResult.Page(
                data = datum.subList(startIndex, endIndex),
                prevKey = if (key > 0) key - 1 else null,
                nextKey = if (endIndex < datum.size) key + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryData>): Int? {
        return state.anchorPosition
    }
}
