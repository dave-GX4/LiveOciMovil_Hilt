package com.updavid.liveoci_hilt.features.auth.di

import com.updavid.liveoci_hilt.features.auth.data.repository.AuthRepositoryImpl
import com.updavid.liveoci_hilt.features.auth.domain.repository.AuthRepositry
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepositry
}