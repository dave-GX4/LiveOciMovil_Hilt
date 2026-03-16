package com.updavid.liveoci_hilt.features.profile.domain.repository

import com.updavid.liveoci_hilt.features.profile.domain.entity.User
import com.updavid.liveoci_hilt.features.profile.domain.entity.UserMessage
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun deleteAccountUser(id: String): UserMessage
    suspend fun getUserByIdRemote(id: String)
    fun getUserRoom(): Flow<User?>
    suspend fun updateNameUser(id: String, name: String): UserMessage
    suspend fun updateEmailUser(id: String, email: String): UserMessage
    suspend fun updatePasswordUser(id: String, password: String): UserMessage
    suspend fun updateTastesUser(
        id: String,
        interests: List<String>,
        topics: List<String>,
        description: String
    ): UserMessage
}