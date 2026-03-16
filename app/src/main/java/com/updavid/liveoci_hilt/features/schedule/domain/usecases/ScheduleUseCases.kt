package com.updavid.liveoci_hilt.features.schedule.domain.usecases

data class ScheduleUseCases(
    val addSchedule : AddScheduleUseCase,
    val updateSchedule : UpdateScheduleUseCase,
    val getSchedulesRoom : GetSchedulesRoomUseCase,
    val getSchedulesRemote: GetSchedulesRemoteUseCase
)
