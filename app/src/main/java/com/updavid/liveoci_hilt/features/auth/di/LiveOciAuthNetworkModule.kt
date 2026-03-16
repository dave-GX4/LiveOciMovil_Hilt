package com.updavid.liveoci_hilt.features.auth.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.api.AuthLiveOciApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LiveOciAuthNetworkModule {
    @Provides
    @Singleton
    fun provideLiveOciAuthApi(@LiveOciRetrofit retrofit: Retrofit): AuthLiveOciApi{
        return retrofit.create(AuthLiveOciApi::class.java)
    }
}