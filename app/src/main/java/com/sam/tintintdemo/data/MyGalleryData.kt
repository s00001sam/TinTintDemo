package com.sam.tintintdemo.data

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

/**
 * 用於 UI 呈現的 Model
 */
@Stable
@Parcelize
data class MyGalleryData(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
) : Parcelable {

    companion object {
        fun RemoteGalleryData.toMyGalleryData() = MyGalleryData(
            id = id,
            albumId = albumId,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl,
        )
    }
}
