package com.sam.tintintdemo.source.datasource

import com.sam.tintintdemo.source.apiservice.TinTintApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: TinTintApiService,
) : BaseDataSource {
}
