package com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper

import com.google.gson.annotations.SerializedName

data class HomeNotificationResponseDto(
    val success: Boolean,
    val data: List<HomeNotificationDto> = emptyList()
)

data class HomeNotificationDto(
    val id: String,
    val userId: String,
    val type: String,
    val title: String,
    val body: String,
    val data: Map<String, Any?>? = null,

    @SerializedName("read")
    val read: Boolean? = null,

    @SerializedName("isRead")
    val isRead: Boolean? = null,

    val channel: String,
    val createdAt: String? = null
)
data class HomeSimpleResponseDto(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null
)