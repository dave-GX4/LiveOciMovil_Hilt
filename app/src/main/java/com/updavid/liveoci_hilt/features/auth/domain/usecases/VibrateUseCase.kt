package com.updavid.liveoci_hilt.features.auth.domain.usecases

import com.updavid.liveoci_hilt.core.hardware.domian.VibrateManager
import javax.inject.Inject

class VibrateUseCase @Inject constructor(
    private val vibrateManager: VibrateManager
) {
    operator fun invoke(durationMillis: Long = 500) {
        vibrateManager.vibrate(durationMillis)
    }
}