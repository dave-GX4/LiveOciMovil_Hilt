package com.updavid.liveoci_hilt.features.activity.domain.usecases

data class ActivityUseCases(
    val createActivity: CreateActivityUseCase,
    val deleteActivity: DeleteActivityUseCase,
    val getAllActivitiesRemote: GetActivitiesRemoteUseCase,
    val getAllActivitiesRoom: GetActivitiesRoomUseCase,
)
