package com.sam.tintintdemo.source.datasource

import com.sam.tintintdemo.data.GalleryData
import retrofit2.Response

interface BaseDataSource {
    suspend fun getGalleryDatum(): Response<List<GalleryData>>
}
