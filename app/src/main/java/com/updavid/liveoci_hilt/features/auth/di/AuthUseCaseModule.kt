package com.updavid.liveoci_hilt.features.auth.di

import com.updavid.liveoci_hilt.features.auth.domain.repository.AuthRepositry
import com.updavid.liveoci_hilt.features.auth.domain.usecases.AuthUseCases
import com.updavid.liveoci_hilt.features.auth.domain.usecases.LoginUseCase
import com.updavid.liveoci_hilt.features.auth.domain.usecases.RegistreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {
    @Provides
    fun providerAuthUseCases( repositry: AuthRepositry): AuthUseCases{
        return AuthUseCases(
            login = LoginUseCase(repositry),
            register = RegistreUseCase(repositry)
        )
    }
}