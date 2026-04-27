package com.updavid.liveoci_hilt.features.home.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper.HomeNotificationResponseDto
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper.HomeSimpleResponseDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeLiveOciApi {

    @GET("notification/{userId}")
    suspend fun getNotifications(
        @Path("userId") userId: String,
        @Query("limit") limit: Int = 20
    ): HomeNotificationResponseDto

    @PATCH("notification/{notificationId}/read")
    suspend fun markNotificationRead(
        @Path("notificationId") notificationId: String
    ): HomeSimpleResponseDto

    @PATCH("notification/user/{userId}/read-all")
    suspend fun markAllNotificationsRead(
        @Path("userId") userId: String
    ): HomeSimpleResponseDto
}