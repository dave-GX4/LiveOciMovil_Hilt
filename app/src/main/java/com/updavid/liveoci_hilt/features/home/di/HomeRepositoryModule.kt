package com.updavid.liveoci_hilt.features.home.di

import com.updavid.liveoci_hilt.features.home.data.repository.NotificationRepositoryImpl
import com.updavid.liveoci_hilt.features.home.domain.repository.NotificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}