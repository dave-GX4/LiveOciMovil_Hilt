package com.updavid.liveoci_hilt.features.user.domain.entity

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val notification: Boolean,
    val interests: List<String>,
    val topics: List<String>,
    val description: String,
    val leisureType: String
)
