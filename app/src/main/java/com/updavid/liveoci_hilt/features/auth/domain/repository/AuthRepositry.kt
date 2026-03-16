package com.updavid.liveoci_hilt.features.auth.domain.repository

import com.updavid.liveoci_hilt.features.auth.domain.entity.AuthMessage

interface AuthRepositry {
    suspend fun login(email: String, password: String): AuthMessage
    suspend fun registre(name: String, email: String, password: String): AuthMessage
}