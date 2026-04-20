package com.updavid.liveoci_hilt.features.user.domain.usescases.user

data class UserUseCases(
    val deleteAccountUser: DeleteAccountUserUseCase,
    val getUserRoom: GetUserRoomUseCase,
    val getUserByIdRemoteUseCase: GetUserByIdRemoteUseCase,
    val updateEmailUserUseCase: UpdateEmailUserUseCase,
    val updateNameUserUseCase: UpdateNameUserUseCase,
    val updatePasswordUserUseCase: UpdatePasswordUserUseCase,
    val updateTastesUserUseCase: UpdateTastesUserUseCase,
    val logout: LogoutUseCase,
)
