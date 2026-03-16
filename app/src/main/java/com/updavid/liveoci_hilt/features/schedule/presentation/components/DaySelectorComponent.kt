package com.updavid.liveoci_hilt.features.schedule.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DaySelectorComponent(
    selectedDays: List<Int>,
    onDayClick: (Int) -> Unit,
    errorMessage: String?
) {
    val daysOfWeek = listOf(
        Pair(1, "L"), Pair(2, "M"), Pair(3, "M"),
        Pair(4, "J"), Pair(5, "V"), Pair(6, "S"), Pair(7, "D")
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Repetir los días:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { (dayId, label) ->
                val isSelected = selectedDays.contains(dayId)

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFF00E676) else Color.Transparent)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color(0xFF00E676) else Color.LightGray,
                            shape = CircleShape
                        )
                        .clickable { onDayClick(dayId) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) Color.White else Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}