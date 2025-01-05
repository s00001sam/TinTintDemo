package com.sam.tintintdemo.source.repo

import com.sam.tintintdemo.data.MyGalleryData
import com.sam.tintintdemo.data.State
import kotlinx.coroutines.flow.Flow

/**
 * Repository 抽象層（解耦），方便抽換資料以及測試
 */
interface BaseRepository {
    suspend fun getGalleryDatum(): Flow<State<List<MyGalleryData>>>
}
