package com.sam.tintintdemo.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sam.tintintdemo.data.MyGalleryData
import com.sam.tintintdemo.data.State
import com.sam.tintintdemo.source.pagingsource.GalleryPagingSource
import com.sam.tintintdemo.source.repo.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: BaseRepository,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String>
        get() = _errorMessage

    private val _galleryDatum = MutableStateFlow<List<MyGalleryData>>(emptyList())
    val galleryDatum: StateFlow<List<MyGalleryData>>
        get() = _galleryDatum

    @OptIn(ExperimentalCoroutinesApi::class)
    val galleryPagingFlow = galleryDatum.flatMapLatest {
        if (it.isEmpty()) {
            flowOf(PagingData.empty())
        } else {
            getGalleryPager(it).flow
        }
    }.cachedIn(viewModelScope)

    init {
        getGalleryDatum()
    }

    private fun getGalleryDatum() {
        viewModelScope.launch {
            repository.getGalleryDatum().collectLatest {
                when (it) {
                    State.Loading -> {
                        _isLoading.tryEmit(true)
                    }

                    is State.Success -> {
                        _isLoading.tryEmit(false)
                        _galleryDatum.tryEmit(it.data)
                    }

                    is State.Error -> {
                        _isLoading.tryEmit(false)
                        _errorMessage.emit(it.throwable.localizedMessage.orEmpty())
                    }
                }
            }
        }
    }

    private fun getGalleryPager(datum: List<MyGalleryData>) = Pager(
        PagingConfig(
            pageSize = 60,
            initialLoadSize = 60,
            prefetchDistance = 20,
        )
    ) {
        GalleryPagingSource(datum)
    }
}
