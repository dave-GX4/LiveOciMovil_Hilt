package com.updavid.liveoci_hilt.features.friends.di

import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRequestRepository
import com.updavid.liveoci_hilt.features.friends.domain.usecases.request.CancelFriendRequestUseCase
import com.updavid.liveoci_hilt.features.friends.domain.usecases.request.FriendRequestUseCases
import com.updavid.liveoci_hilt.features.friends.domain.usecases.request.GetFriendRequestUseCase
import com.updavid.liveoci_hilt.features.friends.domain.usecases.request.ResponseFriendRequestUseCase
import com.updavid.liveoci_hilt.features.friends.domain.usecases.request.SendFriendRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FriendUseCaseModule {
    @Provides
    fun providerFriendRequestUseCases(repository: FriendRequestRepository): FriendRequestUseCases{
        return FriendRequestUseCases(
            getFriendRequest = GetFriendRequestUseCase(repository),
            sendFriendRequest = SendFriendRequestUseCase(repository),
            cancelFriendRequest = CancelFriendRequestUseCase(repository),
            responseFriendRequest = ResponseFriendRequestUseCase(repository)
        )
    }
}