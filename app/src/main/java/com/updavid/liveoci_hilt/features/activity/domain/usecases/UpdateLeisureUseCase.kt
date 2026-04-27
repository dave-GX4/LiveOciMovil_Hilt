package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.entity.UpdateLeisureRequest
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import javax.inject.Inject

class UpdateLeisureUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(id: String, request: UpdateLeisureRequest) =
        repository.updateLeisureRecord(id, request)
}