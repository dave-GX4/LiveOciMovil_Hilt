package com.updavid.liveoci_hilt.features.user.domain.repository

import com.updavid.liveoci_hilt.features.user.domain.entity.User
import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun deleteAccountUser(): Message
    suspend fun getUserByIdRemote()
    fun getUserRoom(): Flow<User?>
    suspend fun updateNameUser(name: String): Message
    suspend fun updateEmailUser(email: String): Message
    suspend fun updatePasswordUser(password: String): Message
    suspend fun updateTastesUser(
        interests: List<String>,
        topics: List<String>,
        description: String
    ): Message
    suspend fun logoutLocal(): Result<Unit>
}