package com.updavid.liveoci_hilt.features.code.domain.repository

import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser

interface CodeRepository {
    suspend fun getCodeOfUser(id: String): Code
    suspend fun searchUserByCode(id: String, code: String): FoundUser
}