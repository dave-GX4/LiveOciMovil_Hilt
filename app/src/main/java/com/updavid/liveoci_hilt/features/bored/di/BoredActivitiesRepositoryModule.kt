package com.updavid.liveoci_hilt.features.bored.di

import com.updavid.liveoci_hilt.features.bored.data.repository.BoredActivitiesRepositoryImpl
import com.updavid.liveoci_hilt.features.bored.domain.repository.BoredActivitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BoredActivitiesRepositoryModule {
    @Binds
    abstract fun bindBoredActivitiesRepository(
        boredActivitiesRepositoryImpl: BoredActivitiesRepositoryImpl
    ): BoredActivitiesRepository
}