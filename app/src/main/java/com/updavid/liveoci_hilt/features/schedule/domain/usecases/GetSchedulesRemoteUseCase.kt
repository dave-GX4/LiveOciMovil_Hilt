package com.updavid.liveoci_hilt.features.schedule.domain.usecases

import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetSchedulesRemoteUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(){
        repository.getAllSchedulesRemote()
    }
}