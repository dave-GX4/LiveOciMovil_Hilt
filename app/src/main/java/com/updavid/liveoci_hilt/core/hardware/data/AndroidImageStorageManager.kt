package com.updavid.liveoci_hilt.core.hardware.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.updavid.liveoci_hilt.core.hardware.domian.ImageStorageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AndroidImageStorageManager @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageStorageManager {

    override fun createTempImageUri(): Uri {
        val tempFile = File(
            context.cacheDir,
            "temp_photo_${System.currentTimeMillis()}.jpg"
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            tempFile
        )
    }
}