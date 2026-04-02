package com.updavid.liveoci_hilt.features.leisure.di

import com.updavid.liveoci_hilt.features.leisure.domain.repository.GeminiRepository
import com.updavid.liveoci_hilt.features.leisure.domain.usecases.GeminiUseCases
import com.updavid.liveoci_hilt.features.leisure.domain.usecases.GenerateActivityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GeminiUseCasesModule {
    @Provides
    fun provideGeminiUseCases(repository: GeminiRepository): GeminiUseCases{
        return GeminiUseCases(
            generateActivity = GenerateActivityUseCase(repository)
        )
    }
}