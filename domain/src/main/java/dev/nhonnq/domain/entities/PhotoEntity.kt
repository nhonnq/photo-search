package dev.nhonnq.domain.entities

import com.google.gson.annotations.SerializedName

data class PhotoEntity(
    @SerializedName("id") override val id: Int,
    @SerializedName("width") override val width: Int?,
    @SerializedName("height") override val height: Int?,
    @SerializedName("url") override val url: String?,
    val photographer: String?,
    val src: SourceUrl?,
) : IPhoto