package com.sam.tintintdemo.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.tintintdemo.data.GalleryData
import com.sam.tintintdemo.data.State
import com.sam.tintintdemo.source.repo.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _galleryDatum = MutableStateFlow<List<GalleryData>>(emptyList())
    val galleryDatum: StateFlow<List<GalleryData>>
        get() = _galleryDatum

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
}
