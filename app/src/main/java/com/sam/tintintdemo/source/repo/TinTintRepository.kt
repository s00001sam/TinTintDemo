package com.sam.tintintdemo.source.repo

import com.sam.tintintdemo.data.MyGalleryData
import com.sam.tintintdemo.data.MyGalleryData.Companion.toMyGalleryData
import com.sam.tintintdemo.data.State
import com.sam.tintintdemo.source.datasource.BaseDataSource
import com.sam.tintintdemo.source.hilt.LocalData
import com.sam.tintintdemo.source.hilt.RemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class TinTintRepository @Inject constructor(
    @LocalData private val localDataSource: BaseDataSource,
    @RemoteData private val remoteDataSource: BaseDataSource,
) : BaseRepository {
    /**
     * 在 Repository 將 API 資料格式轉成 UI 資料格式的 Flow
     */
    override suspend fun getGalleryDatum(): Flow<State<List<MyGalleryData>>> {
        return flow {
            runCatching {
                val response = remoteDataSource.getGalleryDatum()
                when {
                    response.isSuccessful -> {
                        val datum = response.body().orEmpty()
                            .map {
                                it.toMyGalleryData()
                            }
                        emit(State.Success(datum))
                    }

                    else -> {
                        val exception = Throwable("API Error")
                        Timber.e(exception)
                        emit(State.Error(exception))
                    }
                }
            }.onFailure {
                Timber.e(it)
                emit(State.Error(Throwable("Network Error")))
            }
        }.flowOn(Dispatchers.IO)
            .catch {
                emit(State.Error(it))
            }
            .onStart {
                emit(State.Loading)
            }
    }
}
