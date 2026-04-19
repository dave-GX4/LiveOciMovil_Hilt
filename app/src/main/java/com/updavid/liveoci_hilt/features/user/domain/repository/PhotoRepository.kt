package com.updavid.liveoci_hilt.features.user.domain.repository

import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.entity.Photo

interface PhotoRepository {
    suspend fun getPhotoUserRemote(): Photo
    suspend fun uploadPhotoUserRemote(): Message
    suspend fun updatePhotoUserRemote(): Message
}