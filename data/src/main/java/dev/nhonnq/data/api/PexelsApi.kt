package dev.nhonnq.data.api

import dev.nhonnq.domain.entities.PhotoDetails
import dev.nhonnq.domain.model.PexelsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {

    @GET("v1/search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") since: Int,
        @Query("per_page") perPage: Int,
    ): PexelsResponse

    @GET("v1/photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: Int,
    ): PhotoDetails

}