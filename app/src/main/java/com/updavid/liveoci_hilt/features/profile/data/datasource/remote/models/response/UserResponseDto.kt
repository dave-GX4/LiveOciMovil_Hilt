package com.updavid.liveoci_hilt.features.profile.data.datasource.remote.models.response

data class UserResponseDto(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val notification: Boolean,
    val interests: List<String>,
    val topics: List<String>,
    val description: String,
    val leisure_type: String
)