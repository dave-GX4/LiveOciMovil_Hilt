package com.updavid.liveoci_hilt.features.activity.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.api.ActivityLiveOciApi
import com.updavid.liveoci_hilt.features.bored.data.datasource.remote.api.ActivitiesBoredApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActivityNetworkModule {
    @Provides
    @Singleton
    fun providerActivityLiveOciApi(@LiveOciRetrofit retrofit: Retrofit): ActivityLiveOciApi{
        return retrofit.create(ActivityLiveOciApi::class.java)
    }
}