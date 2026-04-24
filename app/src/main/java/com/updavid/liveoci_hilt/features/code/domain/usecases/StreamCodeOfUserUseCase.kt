package com.updavid.liveoci_hilt.features.code.domain.usecases

import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.repository.CodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreamCodeOfUserUseCase @Inject constructor(
    private val repository: CodeRepository
) {
    operator fun invoke(): Flow<Code> {
        return repository.streamCodeOfUser()
    }
}