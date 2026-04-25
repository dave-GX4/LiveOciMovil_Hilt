package com.updavid.liveoci_hilt.features.schedule.presentation.components

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WheelTimePickerDialog(
    initialTime: String,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    // Parseamos la hora inicial ("08:00" -> h=8, m=0)
    val (initH, initM) = initialTime.split(":").map { it.toInt() }

    var selectedHour by remember { mutableStateOf(initH) }
    var selectedMinute by remember { mutableStateOf(initM) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Seleccionar Hora",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ruleta de Horas (0 - 23)
                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 23
                            value = selectedHour
                            setFormatter { String.format("%02d", it) }
                            setOnValueChangedListener { _, _, newVal -> selectedHour = newVal }
                        }
                    }
                )

                Text(text = " : ", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(horizontal = 16.dp))

                // Ruleta de Minutos (0 - 59)
                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 59
                            value = selectedMinute
                            setFormatter { String.format("%02d", it) }
                            setOnValueChangedListener { _, _, newVal -> selectedMinute = newVal }
                        }
                    }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                // Formateamos de vuelta a string "HH:mm"
                val timeString = String.format("%02d:%02d", selectedHour, selectedMinute)
                onTimeSelected(timeString)
            }) {
                Text("Listo")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}