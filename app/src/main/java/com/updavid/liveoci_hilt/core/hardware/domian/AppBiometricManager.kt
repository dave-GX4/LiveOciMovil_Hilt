package com.updavid.liveoci_hilt.core.hardware.domian

import androidx.fragment.app.FragmentActivity
import com.updavid.liveoci_hilt.core.hardware.domian.states.BiometricResult

interface AppBiometricManager {
    fun isBiometricAvailable(): Boolean
    suspend fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String
    ): BiometricResult
}