package com.updavid.liveoci_hilt.features.user.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.api.PhotoLiveOciApi
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.api.UserLiveOciApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LiveOciUserNetworkModule {
    @Provides
    @Singleton
    fun providerLiveOciUserApi(@LiveOciRetrofit retrofit: Retrofit): UserLiveOciApi{
        return retrofit.create(UserLiveOciApi::class.java)
    }

    @Provides
    @Singleton
    fun providerLiveOciPhotoApi(@LiveOciRetrofit retrofit: Retrofit): PhotoLiveOciApi{
        return retrofit.create(PhotoLiveOciApi::class.java)
    }
}