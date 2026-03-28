package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import javax.inject.Inject

class GetActivitiesRemoteUseCase @Inject constructor(
    private val repository: ActivityRepository
){
    suspend operator fun invoke(id_user: String) {
        repository.getAllActivitiesRemote(id_user)
    }
}