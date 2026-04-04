package com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.response

data class AuthResponseDto(
    val data: String,
    val message: String,
    val status: Boolean
)