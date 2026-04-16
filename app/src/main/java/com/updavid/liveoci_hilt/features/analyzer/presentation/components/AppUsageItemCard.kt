package com.updavid.liveoci_hilt.features.analyzer.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.core.system.domain.entity.AppUsageInfo

@Composable
fun AppUsageItemCard(
    appInfo: AppUsageInfo, // Tu modelo de dominio
    maxUsageMillis: Long,
    animationDelay: Int
) {
    // Calculamos el porcentaje relativo al máximo uso (para que la app #1 siempre llene su barra al 100%)
    val targetProgress = (appInfo.timeSpentMillis.toFloat() / maxUsageMillis.toFloat()).coerceIn(0f, 1f)

    // Estado para disparar la animación al iniciar
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimation = true }

    // Animación fluida de la barra
    val animatedProgress by animateFloatAsState(
        targetValue = if (startAnimation) targetProgress else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = animationDelay, easing = FastOutSlowInEasing),
        label = "progressAnim"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    packageName = appInfo.packageName,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = appInfo.appName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "App del sistema", fontSize = 12.sp, color = Color.Gray) // Aquí podrías mapear categorías reales
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = formatTime(appInfo.timeSpentMillis), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Text(text = "Hoy", fontSize = 11.sp, color = Color(0xFF10B981))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de progreso personalizada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color(0xFFF3F4F6), CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = animatedProgress)
                        .height(8.dp)
                        .background(Color(0xFF10B981), CircleShape) // Verde
                )
            }
        }
    }
}

fun formatTime(millis: Long): String {
    val minutes = (millis / (1000 * 60)) % 60
    val hours = (millis / (1000 * 60 * 60))
    return if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
}