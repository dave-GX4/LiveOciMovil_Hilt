package com.updavid.liveoci_hilt.core.hardware.domian

import android.net.Uri

interface ImageStorageManager {
    fun createTempImageUri(): Uri
}