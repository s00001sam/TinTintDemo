package com.sam.tintintdemo.source.datasource

import com.sam.tintintdemo.data.RemoteGalleryData
import retrofit2.Response

interface BaseDataSource {
    suspend fun getGalleryDatum(): Response<List<RemoteGalleryData>>
}
