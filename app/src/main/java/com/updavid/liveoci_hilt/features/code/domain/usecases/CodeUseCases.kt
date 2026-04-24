package com.updavid.liveoci_hilt.features.code.domain.usecases

data class CodeUseCases(
    val searchUserByCode: SearchUserByCodeUseCase,
    val streamCodeOfUser: StreamCodeOfUserUseCase
)
