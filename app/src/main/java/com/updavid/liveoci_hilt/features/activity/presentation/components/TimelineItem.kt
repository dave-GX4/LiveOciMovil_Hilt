package com.updavid.liveoci_hilt.features.activity.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord
import kotlinx.coroutines.delay

@Composable
fun TimelineItem(
    record: LeisureRecord,
    isCompleted: Boolean,
    isLastItem: Boolean,
    isFirstItem: Boolean = false,
    onDeleteClick: (String) -> Unit,
    onRepeatClick: (String) -> Unit,
    onStartClick: (String) -> Unit,
    onStopClick: (String, LeisureRecord) -> Unit
){
    val lineColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
    val circleColor = if (isCompleted) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface
    val circleBorderColor = MaterialTheme.colorScheme.onSurface

    var ticks by remember { mutableLongStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        val maxSeconds = record.activity.durationMinutes * 60L
        if (isRunning) {
            while (ticks < maxSeconds) {
                delay(1000)
                ticks++
            }

            if (isRunning) {
                isRunning = false
                onStopClick(record.id, record)
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight()
                .drawBehind {
                    val circleRadius = 6.dp.toPx()
                    val circleCenterY = 36.dp.toPx()

                    if (!isFirstItem) {
                        drawLine(
                            color = lineColor,
                            start = Offset(size.width / 2, 0f),
                            end = Offset(size.width / 2, circleCenterY - circleRadius),
                            strokeWidth = 2.dp.toPx()
                        )
                    }

                    if (!isLastItem) {
                        drawLine(
                            color = lineColor,
                            start = Offset(size.width / 2, circleCenterY + circleRadius),
                            end = Offset(size.width / 2, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }

                    drawCircle(
                        color = circleColor,
                        radius = circleRadius,
                        center = Offset(size.width / 2, circleCenterY)
                    )

                    drawCircle(
                        color = circleBorderColor,
                        radius = circleRadius,
                        center = Offset(size.width / 2, circleCenterY),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
        )

        Spacer(modifier = Modifier.width(16.dp))

        val cardBackground = if (isCompleted) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.inverseSurface
        val textColor = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.inverseOnSurface

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackground),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column {
                if (!isCompleted && record.activity.imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = record.activity.imageUrl,
                        contentDescription = "Imagen de la actividad",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = record.activity.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = if (isRunning) formatTimer(ticks) else "${record.startTime} - ${record.endTime}",
                            style = MaterialTheme.typography.bodySmall,
                            color = textColor.copy(alpha = 0.7f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = record.activity.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = 0.8f),
                        maxLines = 2
                    )

                    if (isCompleted) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) { index ->
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (index < record.satisfaction) Color(0xFFFFD700) else textColor.copy(alpha = 0.2f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = record.scheduleDate ?: "",
                                style = MaterialTheme.typography.labelSmall,
                                color = textColor.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Categoría",
                                tint = textColor.copy(alpha = 0.7f),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = record.activity.category,
                                style = MaterialTheme.typography.labelMedium,
                                color = textColor.copy(alpha = 0.7f)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(
                                onClick = { onDeleteClick(record.activity.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Borrar Actividad",
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.9f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            if (isCompleted) {
                                IconButton(
                                    onClick = { onRepeatClick(record.activity.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Repetir Actividad",
                                        tint = textColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                if (!isRunning) {
                                    IconButton(
                                        onClick = {
                                            isRunning = true
                                            onStartClick(record.id)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Iniciar",
                                            tint = textColor,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                } else {
                                    IconButton(
                                        onClick = {
                                            isRunning = false
                                            onStopClick(record.id, record)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Stop,
                                            contentDescription = "Detener",
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatTimer(seconds: Long): String {
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60
    return if (h > 0) String.format("%02d:%02d:%02d", h, m, s)
    else String.format("%02d:%02d", m, s)
}