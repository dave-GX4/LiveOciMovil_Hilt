package com.updavid.liveoci_hilt.features.code.domain.repository

import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser
import kotlinx.coroutines.flow.Flow

interface CodeRepository {
    fun streamCodeOfUser(): Flow<Code>
    suspend fun searchUserByCode(code: String): FoundUser
}