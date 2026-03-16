package com.updavid.liveoci_hilt.features.profile.di

import com.updavid.liveoci_hilt.features.profile.data.repository.UserRepositoryImpl
import com.updavid.liveoci_hilt.features.profile.domain.repository.UserRepository
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