package com.updavid.liveoci_hilt.features.user.domain.repository

import com.updavid.liveoci_hilt.features.user.domain.entity.User
import com.updavid.liveoci_hilt.features.user.domain.entity.UserMessage
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun deleteAccountUser(): UserMessage
    suspend fun getUserByIdRemote()
    fun getUserRoom(): Flow<User?>
    suspend fun updateNameUser(name: String): UserMessage
    suspend fun updateEmailUser(email: String): UserMessage
    suspend fun updatePasswordUser(password: String): UserMessage
    suspend fun updateTastesUser(
        interests: List<String>,
        topics: List<String>,
        description: String
    ): UserMessage
    suspend fun logoutLocal(): Result<Unit>
}