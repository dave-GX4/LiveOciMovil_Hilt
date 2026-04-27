package com.updavid.liveoci_hilt.features.home.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.api.HomeLiveOciApi
import com.updavid.liveoci_hilt.features.home.domain.repository.HomeRepository
import com.updavid.liveoci_hilt.features.home.domain.usecase.GetHomeNotificationsUseCase
import com.updavid.liveoci_hilt.features.home.domain.usecase.HomeUseCases
import com.updavid.liveoci_hilt.features.home.domain.usecase.MarkAllHomeNotificationsReadUseCase
import com.updavid.liveoci_hilt.features.home.domain.usecase.MarkHomeNotificationReadUseCase
import com.updavid.liveoci_hilt.features.home.repository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeLiveOciNetworkModule {

    @Provides
    @Singleton
    fun provideHomeLiveOciApi(
        @LiveOciRetrofit retrofit: Retrofit
    ): HomeLiveOciApi {
        return retrofit.create(HomeLiveOciApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        api: HomeLiveOciApi
    ): HomeRepository {
        return HomeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(
        repository: HomeRepository
    ): HomeUseCases {
        return HomeUseCases(
            getHomeNotificationsUseCase = GetHomeNotificationsUseCase(repository),
            markHomeNotificationReadUseCase = MarkHomeNotificationReadUseCase(repository),
            markAllHomeNotificationsReadUseCase = MarkAllHomeNotificationsReadUseCase(repository)
        )
    }
}