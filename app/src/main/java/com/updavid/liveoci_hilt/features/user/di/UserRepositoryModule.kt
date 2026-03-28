package com.updavid.liveoci_hilt.features.user.di

import com.updavid.liveoci_hilt.features.user.data.repository.UserRepositoryImpl
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}