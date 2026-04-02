package com.updavid.liveoci_hilt.features.leisure.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.api.GeminiLiveOciApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeminiNetworkModule {
    @Provides
    @Singleton
    fun providerGeminiLiveOciApi(@LiveOciRetrofit retrofit: Retrofit): GeminiLiveOciApi{
        return retrofit.create(GeminiLiveOciApi::class.java)
    }
}