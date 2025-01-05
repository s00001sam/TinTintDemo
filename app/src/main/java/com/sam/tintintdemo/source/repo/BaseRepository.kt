package com.sam.tintintdemo.source.repo

import com.sam.tintintdemo.data.GalleryData
import com.sam.tintintdemo.data.State
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    suspend fun getGalleryDatum(): Flow<State<List<GalleryData>>>
}
