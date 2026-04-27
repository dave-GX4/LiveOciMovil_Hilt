package com.updavid.liveoci_hilt.features.home.domain.usecase

data class NotificationUseCases(
    val getNotifications: GetNotificationsUseCase,
    val markNotificationRead: MarkNotificationReadUseCase,
    val markAllNotificationsRead: MarkAllNotificationsReadUseCase,
    val streamNotifications: StreamNotificationsUseCase
)