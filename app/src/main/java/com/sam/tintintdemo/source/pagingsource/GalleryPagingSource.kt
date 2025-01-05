package com.sam.tintintdemo.source.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sam.tintintdemo.data.MyGalleryData
import retrofit2.HttpException
import java.io.IOException

/**
 * 透過 Paging3 實現分頁機制
 */
class GalleryPagingSource(
    private val datum: List<MyGalleryData>,
) : PagingSource<Int, MyGalleryData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyGalleryData> {
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

    override fun getRefreshKey(state: PagingState<Int, MyGalleryData>): Int? {
        return state.anchorPosition
    }
}
