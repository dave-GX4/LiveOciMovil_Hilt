package com.updavid.liveoci_hilt.features.schedule.domain.usecases

import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage
import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import kotlin.String

class AddScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
){
    suspend operator fun invoke(
        title: String,
        days: List<Int>,
        startTime: String,
        endTime: String,
        active: Boolean,
        type: String
    ): Result<ScheduleMessage>{
        return try {
            if (type == "escuela" || type == "trabajo") {
                val existingSchedule = repository.getScheduleByTypeRoom(type).firstOrNull()

                if (existingSchedule != null) {
                    return Result.failure(Exception("Ya tienes un horario de $type registrado. Solo se permite uno."))
                }
            }

            val response = repository.addSchedule(
                title,
                days,
                startTime,
                endTime,
                active,
                type
            )

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}