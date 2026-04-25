package com.updavid.liveoci_hilt.features.friends.di

import com.updavid.liveoci_hilt.core.di.LiveOciRetrofit
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.api.FriendRequestLiveOciApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FriendLiveOciNetworkModule {
    @Provides
    @Singleton
    fun providerRequestApi(@LiveOciRetrofit retrofit: Retrofit): FriendRequestLiveOciApi{
        return retrofit.create(FriendRequestLiveOciApi::class.java)
    }
}