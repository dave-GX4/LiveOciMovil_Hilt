package com.updavid.liveoci_hilt.features.user.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.PhotoResponseDto
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface PhotoLiveOciApi {
    @GET("cloudinary/{id}/photo")
    suspend fun getPhotoUser(
        @Path("id") id: String
    ): PhotoResponseDto

    @Multipart
    @PUT("cloudinary/{id}/photo")
    suspend fun saveOrUpdatePhotoUser(
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): MessageResponseDto
}