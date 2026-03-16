package com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.request

data class AuthSingUp(
    val name: String,
    val email: String,
    val  password: String,
)
