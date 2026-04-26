package com.updavid.liveoci_hilt.features.friends.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.CancelRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.RemoveFriendRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.ResponseRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.SendRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.FriendRequestResponseDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.FriendsResponseDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.MessageFriendResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendRequestLiveOciApi {
    @POST("friends/request")
    suspend fun sendRequest(
        @Body request: SendRequestDto
    ): MessageFriendResponseDto
    @PATCH("friends/update/{id}")
    suspend fun responseRequest(
        @Path("id") id: String,
        @Body request: ResponseRequestDto
    ): MessageFriendResponseDto
    @HTTP(method = "DELETE", path = "friends/cancel/{id}", hasBody = true)
    suspend fun cancelRequest(
        @Path("id") id: String,
        @Body request: CancelRequestDto
    ): MessageFriendResponseDto
    @GET("friends/get/pending/{id}")
    suspend fun getPendingRequest(
        @Path("id") id: String
    ): List<FriendRequestResponseDto>
    @GET("friends/all/{id}")
    suspend fun getAllFriends(
        @Path("id") id: String
    ): List<FriendsResponseDto>
    @HTTP(method = "DELETE",path ="friends/remove/{id}", hasBody = true)
    suspend fun removeFriend(
        @Path("id") friendshipId: String,
        @Body request: RemoveFriendRequestDto
    ): MessageFriendResponseDto
}