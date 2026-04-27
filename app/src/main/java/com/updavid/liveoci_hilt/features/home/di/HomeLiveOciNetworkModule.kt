package com.updavid.liveoci_hilt.features.home.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.api.NotificationLiveOciApi
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
    ): NotificationLiveOciApi {
        return retrofit.create(NotificationLiveOciApi::class.java)
    }
}