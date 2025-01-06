package com.sam.tintintdemo.source.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sam.tintintdemo.source.datasource.BaseDataSource
import com.sam.tintintdemo.source.hilt.LocalData
import com.sam.tintintdemo.source.hilt.RemoteData
import com.sam.tintintdemo.source.pagingsource.GalleryPagingSource
import javax.inject.Inject

class TinTintRepository @Inject constructor(
    @LocalData private val localDataSource: BaseDataSource,
    @RemoteData private val remoteDataSource: BaseDataSource,
) : BaseRepository {
    /**
     * 在 Repository 將 API 資料格式轉成 UI 資料格式的 Flow (PagingData Flow)
     */
    override fun getGalleryFlow() = Pager(
        PagingConfig(
            pageSize = 60,
            initialLoadSize = 60,
            prefetchDistance = 20,
        )
    ) {
        GalleryPagingSource(remoteDataSource)
    }.flow
}
