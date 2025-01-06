package com.sam.tintintdemo.source.datasource

import com.sam.tintintdemo.data.RemoteGalleryData
import retrofit2.Response

class LocalDataSource() : BaseDataSource {
    override suspend fun getGalleryDatum(
        limit: Int?,
        page: Int?,
    ): Response<List<RemoteGalleryData>> {
        TODO("Not yet implemented")
    }
}
