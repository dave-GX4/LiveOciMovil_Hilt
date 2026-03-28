package com.updavid.liveoci_hilt.features.bored.di

import com.updavid.liveoci_hilt.features.bored.domain.repository.BoredActivitiesRepository
import com.updavid.liveoci_hilt.features.bored.domain.usecases.BoredActivitiesUseCases
import com.updavid.liveoci_hilt.features.bored.domain.usecases.GetFilterActivitiesUseCase
import com.updavid.liveoci_hilt.features.bored.domain.usecases.GetActivityByKeyUseCase
import com.updavid.liveoci_hilt.features.bored.domain.usecases.GetRandomActivityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BoredActivitiesUseCasesModule {
    @Provides
    fun providerBoredActivitiesUseCases(repository: BoredActivitiesRepository): BoredActivitiesUseCases{
        return BoredActivitiesUseCases(
            getRandomActivity = GetRandomActivityUseCase(repository),
            getFilterActivities = GetFilterActivitiesUseCase(repository),
            getActivityByKey = GetActivityByKeyUseCase(repository)
        )
    }
}