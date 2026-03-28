package com.updavid.liveoci_hilt.features.activity.di

import com.updavid.liveoci_hilt.features.activity.data.repository.ActivityRepositoryImpl
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityRepositoryModule {
    @Binds
    abstract fun bindActivityRepository(
        activityRepositoryImpl: ActivityRepositoryImpl
    ): ActivityRepository
}