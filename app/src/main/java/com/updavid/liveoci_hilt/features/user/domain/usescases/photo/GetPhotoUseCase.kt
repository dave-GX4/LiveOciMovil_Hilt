package com.updavid.liveoci_hilt.features.user.domain.usescases.photo

import com.updavid.liveoci_hilt.features.user.domain.entity.Photo
import com.updavid.liveoci_hilt.features.user.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatching {
            repository.getPhotoUserRemote()
        }
    }
}