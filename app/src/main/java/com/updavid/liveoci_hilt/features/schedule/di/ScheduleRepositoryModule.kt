package com.updavid.liveoci_hilt.features.schedule.di

import com.updavid.liveoci_hilt.features.schedule.data.repository.ScheduleRepositoryImpl
import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleRepositoryModule {
    @Binds
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl : ScheduleRepositoryImpl
    ): ScheduleRepository
}