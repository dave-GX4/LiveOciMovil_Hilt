package com.updavid.liveoci_hilt.features.leisure.di

import com.updavid.liveoci_hilt.features.leisure.data.repository.GeminiRepositoryImpl
import com.updavid.liveoci_hilt.features.leisure.domain.repository.GeminiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GeminiRepositoryModule {
    @Binds
    abstract fun bindGeminiRepository(
        geminiRepositoryImpl: GeminiRepositoryImpl
    ): GeminiRepository
}