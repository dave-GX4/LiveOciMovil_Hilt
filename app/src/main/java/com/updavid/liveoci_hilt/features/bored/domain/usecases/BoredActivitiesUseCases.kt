package com.updavid.liveoci_hilt.features.bored.domain.usecases

data class BoredActivitiesUseCases(
    val getRandomActivity: GetRandomActivityUseCase,
    val getFilterActivities: GetFilterActivitiesUseCase,
    val getActivityByKey: GetActivityByKeyUseCase
)
