package com.updavid.liveoci_hilt.features.user.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.PhotoResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PhotoLiveOciApi {
    @GET("cloudinary/{id}/photo")
    suspend fun getPhotoUser(
        @Path("id") id: String
    ): PhotoResponseDto

    @POST("cloudinary/{id}/photo")
    suspend fun uploadPhotoUser(
        @Path("id") id: String
    ): MessageResponseDto

    @POST("cloudinary/{id}/photo")
    suspend fun updatePhotoUser(
        @Path("id") id: String
    ): MessageResponseDto
}