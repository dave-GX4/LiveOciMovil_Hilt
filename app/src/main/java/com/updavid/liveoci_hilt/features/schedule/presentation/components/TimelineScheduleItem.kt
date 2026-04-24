package com.updavid.liveoci_hilt.features.schedule.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScheduleItem(
    schedule: Schedule,
    isLastItem: Boolean,
    isFirstItem: Boolean = false,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onUpdateSchedule: (Schedule) -> Unit,
    onDelete: () -> Unit
) {
    val lineColor = MaterialTheme.colorScheme.outlineVariant
    val circleColor = MaterialTheme.colorScheme.primary
    var draftSchedule by remember(schedule, isExpanded) { mutableStateOf(schedule) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = draftSchedule.startTime,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            modifier = Modifier
                .width(60.dp)
                .padding(top = 24.dp, end = 8.dp)
        )

        Box(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight()
                .drawBehind {
                    val circleRadius = 5.dp.toPx()
                    val circleCenterY = 32.dp.toPx()

                    if (!isFirstItem) {
                        drawLine(
                            color = lineColor,
                            start = Offset(size.width / 2, 0f),
                            end = Offset(size.width / 2, circleCenterY),
                            strokeWidth = 2.dp.toPx()
                        )
                    }

                    if (!isLastItem) {
                        drawLine(
                            color = lineColor,
                            start = Offset(size.width / 2, circleCenterY),
                            end = Offset(size.width / 2, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }

                    drawCircle(color = circleColor, radius = circleRadius, center = Offset(size.width / 2, circleCenterY))
                    drawCircle(color = Color.White, radius = circleRadius * 0.5f, center = Offset(size.width / 2, circleCenterY))
                }
        )

        Card(
            onClick = onToggleExpand,
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp, top = 8.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (draftSchedule.active)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = draftSchedule.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${draftSchedule.startTime} - ${draftSchedule.endTime}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = formatDays(draftSchedule.days),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expandir",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (isExpanded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Notificar / Activo", style = MaterialTheme.typography.bodyMedium)
                        Switch(
                            checked = draftSchedule.active,
                            onCheckedChange = { isActive ->
                                draftSchedule = draftSchedule.copy(active = isActive)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showStartTimePicker = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(draftSchedule.startTime)
                        }
                        OutlinedButton(
                            onClick = { showEndTimePicker = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(draftSchedule.endTime)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val daysMap = listOf(1 to "L", 2 to "M", 3 to "M", 4 to "J", 5 to "V", 6 to "S", 0 to "D")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        daysMap.forEach { (dayValue, label) ->
                            val isSelected = draftSchedule.days.contains(dayValue)
                            DayCircleSmall(
                                label = label,
                                isSelected = isSelected,
                                onClick = {
                                    val newDays = if (isSelected) draftSchedule.days - dayValue else draftSchedule.days + dayValue
                                    draftSchedule = draftSchedule.copy(days = newDays)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onDelete,
                            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar Horario")
                        }

                        Button(
                            onClick = {
                                onUpdateSchedule(draftSchedule) // Guardamos en base de datos
                                onToggleExpand() // Cerramos la tarjeta
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Actualizar")
                        }
                    }
                }
            }
        }
    }

    if (showStartTimePicker) {
        WheelTimePickerDialog(
            initialTime = draftSchedule.startTime,
            onDismiss = { showStartTimePicker = false },
            onTimeSelected = { newTime ->
                draftSchedule = draftSchedule.copy(startTime = newTime)
                showStartTimePicker = false
            }
        )
    }

    if (showEndTimePicker) {
        WheelTimePickerDialog(
            initialTime = draftSchedule.endTime,
            onDismiss = { showEndTimePicker = false },
            onTimeSelected = { newTime ->
                draftSchedule = draftSchedule.copy(endTime = newTime)
                showEndTimePicker = false
            }
        )
    }
}

@Composable
fun DayCircleSmall(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

fun formatDays(days: List<Int>): String {
    val dayNames = mapOf(1 to "Lun", 2 to "Mar", 3 to "Mié", 4 to "Jue", 5 to "Vie", 6 to "Sáb", 7 to "Dom")
    return days.sorted().joinToString(", ") { dayNames[it] ?: "" }
}