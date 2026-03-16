package com.updavid.liveoci_hilt.features.schedule.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.api.ScheduleLiveOciApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LiveOciScheduleNetworkModule {
    @Provides
    @Singleton
    fun providerLiveOciScheduleApi(@LiveOciRetrofit retrofit: Retrofit): ScheduleLiveOciApi{
        return retrofit.create(ScheduleLiveOciApi::class.java)
    }
}