package com.sam.tintintdemo.source.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sam.tintintdemo.data.MyGalleryData
import com.sam.tintintdemo.data.MyGalleryData.Companion.toMyGalleryData
import com.sam.tintintdemo.source.datasource.BaseDataSource
import retrofit2.HttpException
import java.io.IOException

/**
 * 透過 Paging3 實現分頁機制
 */
class GalleryPagingSource(
    private val dataSource: BaseDataSource,
) : PagingSource<Int, MyGalleryData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyGalleryData> {
        return try {
            val pageNumber = if (params is LoadParams.Refresh) 1 else params.key ?: 1
            val response = dataSource.getGalleryDatum(
                page = pageNumber,
                limit = params.loadSize,
            )
            val datum = response.body()?.map {
                it.toMyGalleryData()
            }
            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (!datum.isNullOrEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = datum.orEmpty(),
                prevKey = prevKey,
                nextKey = nextKey
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
