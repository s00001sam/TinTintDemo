package com.sam.tintintdemo.source.datasource

import com.sam.tintintdemo.data.GalleryData
import retrofit2.Response

class LocalDataSource() : BaseDataSource {
    override suspend fun getGalleryDatum(): Response<List<GalleryData>> {
        TODO("Not yet implemented")
    }
}
