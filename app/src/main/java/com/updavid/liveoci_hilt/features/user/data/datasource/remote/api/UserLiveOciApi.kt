package com.updavid.liveoci_hilt.features.user.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.request.UserRequestDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserLiveOciApi {
    @DELETE("user/delete/{id}")
    suspend fun deleteAccount(
        @Path("id") id: String
    ): MessageResponseDto

    @GET("user/get/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): UserResponseDto

    @PATCH("user/update/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body request: UserRequestDto
    ): MessageResponseDto
}