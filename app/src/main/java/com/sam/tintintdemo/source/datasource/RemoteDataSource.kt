package com.sam.tintintdemo.source.datasource

import com.sam.tintintdemo.data.RemoteGalleryData
import com.sam.tintintdemo.source.apiservice.TinTintApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: TinTintApiService,
) : BaseDataSource {
    override suspend fun getGalleryDatum(): Response<List<RemoteGalleryData>> {
        return apiService.getGalleryDatum()
    }
}
