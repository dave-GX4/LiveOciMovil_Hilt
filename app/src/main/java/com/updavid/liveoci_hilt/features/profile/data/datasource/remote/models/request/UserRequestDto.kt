package com.updavid.liveoci_hilt.features.profile.data.datasource.remote.models.request

data class UserRequestDto(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val notification: Boolean? = null,
    val interests: List<String>? = null,
    val topics: List<String>? = null,
    val description: String? = null,
    val leisure_type: String? = null
)