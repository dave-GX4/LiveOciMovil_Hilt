package com.updavid.liveoci_hilt.features.bored.di

import com.updavid.liveoci_hilt.core.di.BoredRetrofit
import com.updavid.liveoci_hilt.features.bored.data.datasource.remote.api.ActivitiesBoredApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BoredActivitiesNetworkModule {
    @Provides
    @Singleton
    fun providerBoredActivitiesApi(@BoredRetrofit retrofit: Retrofit): ActivitiesBoredApi{
        return retrofit.create(ActivitiesBoredApi::class.java)
    }
}