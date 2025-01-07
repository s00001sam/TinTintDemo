package com.sam.tintintdemo.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sam.tintintdemo.source.repo.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: BaseRepository,
) : ViewModel() {
    val galleryPagingFlow = repository.getGalleryFlow().cachedIn(viewModelScope)
}
