package com.updavid.liveoci_hilt.features.friends.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.CancelRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.ResponseRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.SendRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.FriendRequestResponseDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.MessageFriendResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendRequestLiveOciApi {
    @GET("code/get/pending/{id}")
    suspend fun getPendingRequest(
        @Path("id") id: String
    ): FriendRequestResponseDto

    @POST("code/request")
    suspend fun sendRequest(
        @Body request: SendRequestDto
    ): MessageFriendResponseDto

    @DELETE("code/cancel/{id}")
    suspend fun cancelRequest(
        @Path("id") id: String,
        @Body request: CancelRequestDto
    ): MessageFriendResponseDto

    @PATCH("code/update/{id}")
    suspend fun responseRequest(
        @Path("id") id: String,
        @Body request: ResponseRequestDto
    ): MessageFriendResponseDto
}