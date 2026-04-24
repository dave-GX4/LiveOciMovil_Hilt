package com.updavid.liveoci_hilt.features.code.di

import com.updavid.liveoci_hilt.features.code.data.repository.CodeRepositoryImpl
import com.updavid.liveoci_hilt.features.code.domain.repository.CodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CodeRepositoryModule {
    @Binds
    abstract fun bindCodeRepository(
        impl: CodeRepositoryImpl
    ): CodeRepository
}