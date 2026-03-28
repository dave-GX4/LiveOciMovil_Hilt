package com.updavid.liveoci_hilt.features.bored.domain.entity

data class BoredActivity(
    val activity: String,
    val availability: Float,
    val type: String,
    val  participants: Int,
    val price: Float,
    val accessibility: String,
    val duration: String,
    val kidFriendly: Boolean,
    val link: String,
    val key: String
)
