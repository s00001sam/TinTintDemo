package com.sam.tintintdemo.source.repo

import androidx.paging.PagingData
import com.sam.tintintdemo.data.MyGalleryData
import kotlinx.coroutines.flow.Flow

/**
 * Repository 抽象層（解耦），方便抽換資料以及測試
 */
interface BaseRepository {
    fun getGalleryFlow(): Flow<PagingData<MyGalleryData>>
}
