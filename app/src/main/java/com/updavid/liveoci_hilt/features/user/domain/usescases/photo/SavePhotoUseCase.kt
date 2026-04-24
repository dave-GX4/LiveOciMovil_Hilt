package com.updavid.liveoci_hilt.features.user.domain.usescases.photo

import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.repository.PhotoRepository
import java.io.File
import javax.inject.Inject

class SavePhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(imageFile: File): Result<Message> {
        return runCatching {
            repository.saveOrUpdatePhotoUserRemote(imageFile)
        }
    }
}