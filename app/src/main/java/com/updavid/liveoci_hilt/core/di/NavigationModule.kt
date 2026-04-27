package com.updavid.liveoci_hilt.core.di

import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.features.activity.navigation.ActivityNavGraph
import com.updavid.liveoci_hilt.features.analyzer.navigation.AnalyzerNavGraph
import com.updavid.liveoci_hilt.features.user.navigation.UserNavGraph
import com.updavid.liveoci_hilt.features.auth.navigation.AuthNavGraph
import com.updavid.liveoci_hilt.features.bored.navigation.BoredActivitiesNavGraph
import com.updavid.liveoci_hilt.features.code.navigation.CodeNavGraph
import com.updavid.liveoci_hilt.features.friends.navigation.FriendNavGraph
import com.updavid.liveoci_hilt.features.home.navigation.PresentationNavGraph
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

    @Binds
    @IntoSet
    abstract fun bindUserNavGraph(
        userNavGraph: UserNavGraph
    ): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindBoredActivitiesNavGraph(
        boredActivitiesNavGraph: BoredActivitiesNavGraph
    ): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindAnalyzerNavGraph(
        analyzerNavGraph: AnalyzerNavGraph
    ): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindCodeNavGraph(
        codeNavGraph: CodeNavGraph
    ): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindFriendNavGraph(
        friendNavGraph: FriendNavGraph
    ): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindActivityNavGraph(
        activityNavGraph: ActivityNavGraph
    ): FeatureNavGraph
}