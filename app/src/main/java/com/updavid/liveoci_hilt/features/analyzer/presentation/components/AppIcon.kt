package com.updavid.liveoci_hilt.features.analyzer.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppIcon(
    packageName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // remember nos ayuda a no recalcular esto en cada recomposición
    val imageBitmap = remember(packageName) {
        try {
            // Buscamos el ícono real en el sistema
            val drawable = context.packageManager.getApplicationIcon(packageName)
            // Lo convertimos a un Bitmap de Compose
            drawable.toBitmap().asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "Ícono de $packageName",
            modifier = modifier
        )
    } else {
        Box(
            modifier = modifier.background(Color(0xFFE0E7FF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Android, contentDescription = null, tint = Color(0xFF6366F1))
        }
    }
}