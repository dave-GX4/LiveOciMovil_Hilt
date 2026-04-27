package com.updavid.liveoci_hilt.features.home.di

import com.updavid.liveoci_hilt.features.home.domain.repository.NotificationRepository
import com.updavid.liveoci_hilt.features.home.domain.usecase.GetNotificationsUseCase
import com.updavid.liveoci_hilt.features.home.domain.usecase.MarkAllNotificationsReadUseCase
import com.updavid.liveoci_hilt.features.home.domain.usecase.MarkNotificationReadUseCase
import com.updavid.liveoci_hilt.features.home.domain.usecase.NotificationUseCases
import com.updavid.liveoci_hilt.features.home.domain.usecase.StreamNotificationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeUseCasesModule {
    @Provides
    fun providerNotificationUseCases(repository: NotificationRepository): NotificationUseCases{
        return NotificationUseCases(
            getNotifications = GetNotificationsUseCase(repository),
            markNotificationRead = MarkNotificationReadUseCase(repository),
            markAllNotificationsRead = MarkAllNotificationsReadUseCase(repository),
            streamNotifications = StreamNotificationsUseCase(repository)
        )
    }
}