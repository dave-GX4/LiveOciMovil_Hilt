package com.updavid.liveoci_hilt.features.user.di

import com.updavid.liveoci_hilt.features.user.domain.repository.PhotoRepository
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import com.updavid.liveoci_hilt.features.user.domain.usescases.photo.GetPhotoUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.photo.PhotoUseCases
import com.updavid.liveoci_hilt.features.user.domain.usescases.photo.SavePhotoUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.DeleteAccountUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.GetUserByIdRemoteUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.GetUserRoomUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.LogoutUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UpdateEmailUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UpdateNameUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UpdatePasswordUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UpdateTastesUserUseCase
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UserUseCases
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

    @Provides
    fun providerPhotoUseCase(repository: PhotoRepository): PhotoUseCases{
        return PhotoUseCases(
            getPhoto = GetPhotoUseCase(repository),
            savePhoto = SavePhotoUseCase(repository),
        )
    }
}