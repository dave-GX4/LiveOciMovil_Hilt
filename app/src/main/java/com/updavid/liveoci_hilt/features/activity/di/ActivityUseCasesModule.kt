package com.updavid.liveoci_hilt.features.activity.di

import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import com.updavid.liveoci_hilt.features.activity.domain.usecases.ActivityUseCases
import com.updavid.liveoci_hilt.features.activity.domain.usecases.CreateActivityUseCase
import com.updavid.liveoci_hilt.features.activity.domain.usecases.DeleteActivityUseCase
import com.updavid.liveoci_hilt.features.activity.domain.usecases.GetActivitiesRemoteUseCase
import com.updavid.liveoci_hilt.features.activity.domain.usecases.GetActivitiesRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ActivityUseCasesModule {
    @Provides
    fun providerActivityUseCases(repository: ActivityRepository): ActivityUseCases{
        return ActivityUseCases(
            createActivity = CreateActivityUseCase(repository),
            deleteActivity = DeleteActivityUseCase(repository),
            getAllActivitiesRemote = GetActivitiesRemoteUseCase(repository),
            getAllActivitiesRoom = GetActivitiesRoomUseCase(repository)
        )
    }
}