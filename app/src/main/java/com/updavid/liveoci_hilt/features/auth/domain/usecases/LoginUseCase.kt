package com.updavid.liveoci_hilt.features.auth.domain.usecases

import com.updavid.liveoci_hilt.features.auth.domain.entity.AuthMessage
import com.updavid.liveoci_hilt.features.auth.domain.repository.AuthRepositry
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repositry: AuthRepositry
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthMessage>{
        return try {
            val response = repositry.login(email, password)

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}