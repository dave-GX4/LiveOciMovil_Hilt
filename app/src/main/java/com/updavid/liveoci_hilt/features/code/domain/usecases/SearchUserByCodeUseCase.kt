package com.updavid.liveoci_hilt.features.code.domain.usecases

import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser
import com.updavid.liveoci_hilt.features.code.domain.repository.CodeRepository
import javax.inject.Inject

class SearchUserByCodeUseCase @Inject constructor(
    private val repository: CodeRepository
) {
    suspend operator fun invoke(code: String): FoundUser {

        if (code.isBlank()) {
            throw Exception("El código no puede estar vacío")
        }

        return repository.searchUserByCode(code)
    }
}