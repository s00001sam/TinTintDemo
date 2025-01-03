package com.sam.tintintdemo.source.repo

import com.sam.tintintdemo.source.datasource.BaseDataSource
import com.sam.tintintdemo.source.hilt.LocalData
import com.sam.tintintdemo.source.hilt.RemoteData
import javax.inject.Inject

class TinTintRepository @Inject constructor(
    @LocalData private val localDataSource: BaseDataSource,
    @RemoteData private val remoteDataSource: BaseDataSource,
) : BaseRepository {
}
