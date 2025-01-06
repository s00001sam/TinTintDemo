package com.sam.tintintdemo.source.datasource

import com.sam.tintintdemo.data.RemoteGalleryData
import com.sam.tintintdemo.source.apiservice.TinTintApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: TinTintApiService,
) : BaseDataSource {
    override suspend fun getGalleryDatum(
        limit: Int?,
        page: Int?,
    ): Response<List<RemoteGalleryData>> {
        return apiService.getGalleryDatum(limit = limit, page = page)
    }
}
