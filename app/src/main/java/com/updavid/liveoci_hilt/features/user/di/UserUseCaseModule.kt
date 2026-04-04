package com.updavid.liveoci_hilt.features.user.di

import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import com.updavid.liveoci_hilt.features.user.domain.usescases.DeleteAccountUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.GetUserByIdRemoteUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.GetUserRoomUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.LogoutUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.UpdateEmailUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.UpdateNameUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.UpdatePasswordUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.UpdateTastesUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {
    @Provides
    fun providerUserUseCase(repository: UserRepository): UserUseCases{
        return UserUseCases(
            deleteAccountUser = DeleteAccountUserUseCase(repository),
            getUserRoom = GetUserRoomUseCase(repository),
            getUserByIdRemoteUseCase = GetUserByIdRemoteUseCase(repository),
            updateEmailUserUseCase = UpdateEmailUserUseCase(repository),
            updateNameUserUseCase = UpdateNameUserUseCase(repository),
            updatePasswordUserUseCase = UpdatePasswordUserUseCase(repository),
            updateTastesUserUseCase = UpdateTastesUserUseCase(repository),
            logout = LogoutUseCase(repository),
        )
    }
}