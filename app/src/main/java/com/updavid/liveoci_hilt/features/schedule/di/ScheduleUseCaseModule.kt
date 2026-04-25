package com.updavid.liveoci_hilt.features.schedule.di

import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.AddScheduleUseCase
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.DeleteScheduleUseCase
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.GetScheduleByIdUseCase
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.GetSchedulesRemoteUseCase
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.GetSchedulesRoomUseCase
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.ScheduleUseCases
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.UpdateScheduleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ScheduleUseCaseModule {
    @Provides
    fun providerScheduleUseCases(repository: ScheduleRepository): ScheduleUseCases{
        return ScheduleUseCases(
            addSchedule = AddScheduleUseCase(repository),
            updateSchedule = UpdateScheduleUseCase(repository),
            getSchedulesRoom = GetSchedulesRoomUseCase(repository),
            getSchedulesRemote = GetSchedulesRemoteUseCase(repository),
            getScheduleById = GetScheduleByIdUseCase(repository),
            deleteSchedule = DeleteScheduleUseCase(repository),
        )
    }
}