package com.sam.tintintdemo.source.apiservice

import com.sam.tintintdemo.data.GalleryData
import retrofit2.Response
import retrofit2.http.GET

interface TinTintApiService {
    @GET("photos")
    suspend fun getGalleryDatum(): Response<List<GalleryData>>
}
