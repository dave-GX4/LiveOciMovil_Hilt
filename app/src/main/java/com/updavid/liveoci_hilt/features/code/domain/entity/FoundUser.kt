package com.updavid.liveoci_hilt.features.code.domain.entity

data class FoundUser(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val code: String,
    val requestId: String?,
    val requestStatus: String?,
    val isRequester: Boolean?
)