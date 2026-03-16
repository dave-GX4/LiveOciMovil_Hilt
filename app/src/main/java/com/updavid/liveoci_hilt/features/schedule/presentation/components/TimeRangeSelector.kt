package com.updavid.liveoci_hilt.features.schedule.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun TimeRangeSelector(
    startTime: String,
    endTime: String,
    onStartTimeSelected: (String) -> Unit,
    onEndTimeSelected: (String) -> Unit,
    errorMessage: String?
) {
    val context = LocalContext.current

    fun openTimePicker(currentTime: String, onTimePicked: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)

        // Si ya hay una hora seleccionada, abrir el reloj en esa hora
        if (currentTime.isNotBlank() && currentTime.contains(":")) {
            val parts = currentTime.split(":")
            hour = parts[0].toIntOrNull() ?: hour
            minute = parts[1].toIntOrNull() ?: minute
        }

        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                onTimePicked(formattedTime)
            },
            hour, minute, true
        ).show()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimeCard(
                title = "Inicio",
                timeValue = startTime.ifBlank { "--:--" },
                modifier = Modifier.weight(1f),
                onClick = { openTimePicker(startTime, onStartTimeSelected) }
            )

            TimeCard(
                title = "Fin",
                timeValue = endTime.ifBlank { "--:--" },
                modifier = Modifier.weight(1f),
                onClick = { openTimePicker(endTime, onEndTimeSelected) }
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun TimeCard(
    title: String,
    timeValue: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(24.dp))
                .clickable { onClick() }
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            val isSelected = timeValue != "--:--"
            val textColor = if (isSelected) Color(0xFF00E676) else Color.LightGray

            Text(
                text = timeValue,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}