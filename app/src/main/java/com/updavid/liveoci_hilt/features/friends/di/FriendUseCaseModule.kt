package com.updavid.liveoci_hilt.features.friends.di

import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import com.updavid.liveoci_hilt.features.friends.domain.usecases.friend.DeleteFriendUseCase
import com.updavid.liveoci_hilt.features.friends.domain.usecases.friend.FriendUseCases
import com.updavid.liveoci_hilt.features.friends.domain.usecases.friend.GetAllFriendsUseCase
import com.updavid.liveoci_hilt.features.friends.domain.usecases.friend.StreamFriendUpdatesUseCase
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
    fun providerFriendRequestUseCases(repository: FriendRepository): FriendRequestUseCases{
        return FriendRequestUseCases(
            getFriendRequest = GetFriendRequestUseCase(repository),
            sendFriendRequest = SendFriendRequestUseCase(repository),
            cancelFriendRequest = CancelFriendRequestUseCase(repository),
            responseFriendRequest = ResponseFriendRequestUseCase(repository)
        )
    }

    @Provides
    fun providerFriendUseCases(repository: FriendRepository): FriendUseCases{
        return FriendUseCases(
            deleteFriend = DeleteFriendUseCase(repository),
            getAllFriends = GetAllFriendsUseCase(repository),
            streamFriendUpdates = StreamFriendUpdatesUseCase(repository)
        )
    }
}