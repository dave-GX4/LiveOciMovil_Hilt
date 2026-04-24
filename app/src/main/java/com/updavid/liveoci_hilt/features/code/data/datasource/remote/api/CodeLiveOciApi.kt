package com.updavid.liveoci_hilt.features.code.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.response.CodeResponseDto
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.response.FoundUserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CodeLiveOciApi {
    @POST("/code/search/{id}")
    suspend fun searchUserByCode(
        @Path("id") id: String,
        @Body code: String
    ): FoundUserResponseDto

    @GET("/code/stream/{id}")
    suspend fun getCodeFriendOfUser(
        @Path("id") id: String
    ): CodeResponseDto
}