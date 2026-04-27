package com.updavid.liveoci_hilt.features.home.domain.usecase

data class HomeUseCases(
    val getHomeNotificationsUseCase: GetHomeNotificationsUseCase,
    val markHomeNotificationReadUseCase: MarkHomeNotificationReadUseCase,
    val markAllHomeNotificationsReadUseCase: MarkAllHomeNotificationsReadUseCase
)