package com.updavid.liveoci_hilt.features.user.domain.usescases.photo

import android.net.Uri
import com.updavid.liveoci_hilt.core.hardware.domian.ImageStorageManager
import javax.inject.Inject

class CreateImageUriUseCase @Inject constructor(
    private val imageStorageManager: ImageStorageManager
) {
    operator fun invoke(): Uri {
        return imageStorageManager.createTempImageUri()
    }
}