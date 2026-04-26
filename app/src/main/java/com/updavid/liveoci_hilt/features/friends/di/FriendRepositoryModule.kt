package com.updavid.liveoci_hilt.features.friends.di

import com.updavid.liveoci_hilt.features.friends.data.repository.FriendRequestRepositoryImpl
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FriendRepositoryModule {
    @Binds
    abstract fun bindFriendRepository(
        impl: FriendRequestRepositoryImpl
    ): FriendRepository
}