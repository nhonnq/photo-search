package dev.nhonnq.domain.entities

import com.google.gson.annotations.SerializedName

data class PhotoDetails(
    @SerializedName("id") override val id: Int,
    @SerializedName("width") override val width: Int? = null,
    @SerializedName("height") override val height: Int? = null,
    @SerializedName("url") override val url: String? = null,
    @SerializedName("photographer") val photographer: String? = null,
    @SerializedName("photographer_url") val photographerUrl: String? = null,
    @SerializedName("src") val src: SourceUrl? = null,
    @SerializedName("avg_color") val avgColor: String? = null,
    @SerializedName("liked") val liked: Boolean? = false,
    @SerializedName("alt") val alt: String? = null,
) : IPhoto