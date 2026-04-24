package com.updavid.liveoci_hilt.features.code.di

import com.updavid.liveoci_hilt.features.code.domain.repository.CodeRepository
import com.updavid.liveoci_hilt.features.code.domain.usecases.CodeUseCases
import com.updavid.liveoci_hilt.features.code.domain.usecases.SearchUserByCodeUseCase
import com.updavid.liveoci_hilt.features.code.domain.usecases.StreamCodeOfUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CodeUseCasesModule {
    @Provides
    fun providerCodeUseCases(repository: CodeRepository): CodeUseCases{
        return CodeUseCases(
            searchUserByCode = SearchUserByCodeUseCase(repository),
            streamCodeOfUser = StreamCodeOfUserUseCase(repository)
        )
    }
}