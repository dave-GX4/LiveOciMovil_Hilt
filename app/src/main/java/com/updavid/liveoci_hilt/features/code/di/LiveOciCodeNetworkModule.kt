package com.updavid.liveoci_hilt.features.code.di

import com.google.gson.Gson
import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.core.di.StreamingClient
import com.updavid.liveoci_hilt.core.ssedatasource.SseDataSource
import com.updavid.liveoci_hilt.core.ssedatasource.SseDataSourceImpl
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.api.CodeLiveOciApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LiveOciCodeNetworkModule {
    @Provides
    @Singleton
    fun providerLiveOciCodeApi(@LiveOciRetrofit retrofit: Retrofit): CodeLiveOciApi{
        return retrofit.create(CodeLiveOciApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCodeSseDataSource(
        @StreamingClient okHttpClient: OkHttpClient,
        gson: Gson
    ): SseDataSource {
        return SseDataSourceImpl(okHttpClient, gson)
    }
}