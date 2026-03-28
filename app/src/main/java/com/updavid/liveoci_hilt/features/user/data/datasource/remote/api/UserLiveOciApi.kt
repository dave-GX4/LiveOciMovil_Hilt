package com.updavid.liveoci_hilt.features.user.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.request.UserRequestDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.UserResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserLiveOciApi {
    @DELETE("")
    suspend fun deleteAccount(
        @Path("id") id: String
    ): MessageResponseDto

    @GET("")
    suspend fun getUserById(
        @Path("id") id: String
    ): UserResponseDto

    @PATCH("")
    suspend fun updateUser(
        @Path("id") id: String,
        request: UserRequestDto
    ): MessageResponseDto
}