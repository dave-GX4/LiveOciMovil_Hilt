package com.updavid.liveoci_hilt.features.user.domain.usescases.user

import androidx.fragment.app.FragmentActivity
import com.updavid.liveoci_hilt.core.hardware.domian.AppBiometricManager
import com.updavid.liveoci_hilt.core.hardware.domian.states.BiometricResult
import javax.inject.Inject

class AuthenticateBiometricUseCase @Inject constructor(
    private val biometricManager: AppBiometricManager
) {
    fun isAvailable() = biometricManager.isBiometricAvailable()

    suspend operator fun invoke(activity: FragmentActivity): BiometricResult {
        return biometricManager.authenticate(
            activity = activity,
            title = "Iniciar Sesión",
            subtitle = "Usa tu huella para entrar a tu perfil"
        )
    }
}