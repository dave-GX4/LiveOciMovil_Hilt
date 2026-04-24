package com.updavid.liveoci_hilt.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()

    @Provides
    @Singleton
    fun provideNormalOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @StreamingClient
    fun provideStreamingOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @LiveOciRetrofit
    fun provideLiveOciRetrofit(
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-live-oci-production.up.railway.app/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}