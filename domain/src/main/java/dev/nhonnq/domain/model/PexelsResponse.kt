package dev.nhonnq.domain.model

import com.google.gson.annotations.SerializedName
import dev.nhonnq.domain.entities.PhotoEntity

data class PexelsResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("photos") val photos: List<PhotoEntity>?,
    @SerializedName("next_page") val nextPage: String?,
    @SerializedName("prev_page") val prevPage: String?,
)