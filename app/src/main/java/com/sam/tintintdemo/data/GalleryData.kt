package com.sam.tintintdemo.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryData(
    @Json(name = "id") val _id: Int? = null,
    @Json(name = "albumId") val _albumId: Int? = null,
    @Json(name = "title") val _title: String? = null,
    @Json(name = "url") val _url: String? = null,
    @Json(name = "thumbnailUrl") val _thumbnailUrl: String? = null,
) : Parcelable {
    val id: Int
        get() = _id ?: -1

    val albumId: Int
        get() = _albumId ?: -1

    val title: String
        get() = _title.orEmpty()

    val url: String
        get() = _url.orEmpty()

    val thumbnailUrl: String
        get() = _thumbnailUrl.orEmpty()
}
