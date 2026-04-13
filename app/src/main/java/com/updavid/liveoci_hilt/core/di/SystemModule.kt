package com.updavid.liveoci_hilt.core.di

import android.content.Context
import com.updavid.liveoci_hilt.core.system.data.serviceImpl.AppUsageTrackerImpl
import com.updavid.liveoci_hilt.core.system.domain.service.AppUsageTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemModule {

    @Provides
    @Singleton
    fun provideAppUsageTracker(
        @ApplicationContext context: Context
    ): AppUsageTracker {
        return AppUsageTrackerImpl(context)
    }
}