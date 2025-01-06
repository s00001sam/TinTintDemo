package com.sam.tintintdemo.source.apiservice

import com.sam.tintintdemo.data.RemoteGalleryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TinTintApiService {
    @GET("photos")
    suspend fun getGalleryDatum(
        @Query("_limit") limit: Int? = null,
        @Query("_page") page: Int? = null,
    ): Response<List<RemoteGalleryData>>
}
