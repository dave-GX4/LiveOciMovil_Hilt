package com.updavid.liveoci_hilt.features.user.domain.repository

import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.entity.Photo
import java.io.File

interface PhotoRepository {
    suspend fun getPhotoUserRemote()
    suspend fun saveOrUpdatePhotoUserRemote(imageFile: File): Message
}