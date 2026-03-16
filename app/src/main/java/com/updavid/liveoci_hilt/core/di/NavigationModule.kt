package com.updavid.liveoci_hilt.core.di

import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.features.auth.navigation.AuthNavGraph
import com.updavid.liveoci_hilt.features.presentation.navigation.PresentationNavGraph
import com.updavid.liveoci_hilt.features.schedule.navigation.ScheduleNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    @IntoSet
    abstract fun bindAuthNavGraph(
        authNavGraph: AuthNavGraph
    ): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindScheduleNavGraph(
        scheduleNavGraph: ScheduleNavGraph
    ): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindPresentationNavGraph(
        presentationNavGraph: PresentationNavGraph
    ): FeatureNavGraph
}