package com.updavid.liveoci_hilt.features.schedule.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.features.schedule.presentation.components.CategoryButton
import com.updavid.liveoci_hilt.features.schedule.presentation.components.DayCircle
import com.updavid.liveoci_hilt.features.schedule.presentation.components.WheelTimePickerDialog
import com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels.FormScheduleViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormSchedulePage(
    viewModel: FormScheduleViewModel,
    onBack: () -> Unit,
    onSuccessSaved: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(state.successMessage, state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            viewModel.clearMessages()
        }
        state.successMessage?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            viewModel.clearMessages()
            delay(1000)
            onSuccessSaved()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Horario") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Atrás") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Título", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.updateTitle(it) },
                placeholder = { Text("Ej. Compras...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Categoría", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Cambiamos "special", "school", "work" por los valores del backend
                CategoryButton("Especial", state.type == "personalizado", Modifier.weight(1f)) {
                    viewModel.updateType("personalizado")
                }
                CategoryButton("Escuela", state.type == "escuela", Modifier.weight(1f)) {
                    viewModel.updateType("escuela")
                }
                CategoryButton("Trabajo", state.type == "trabajo", Modifier.weight(1f)) {
                    viewModel.updateType("trabajo")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Días", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                val daysMap = listOf(
                    1 to "L",
                    2 to "M",
                    3 to "M",
                    4 to "J",
                    5 to "V",
                    6 to "S",
                    0 to "D"
                )
                daysMap.forEach { (dayValue, label) ->
                    DayCircle(
                        label = label,
                        isSelected = state.days.contains(dayValue),
                        onClick = { viewModel.toggleDay(dayValue) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Hora Inicio", fontWeight = FontWeight.Bold)
                    OutlinedButton(
                        onClick = { showStartTimePicker = true },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(state.startTime, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Hora Fin", fontWeight = FontWeight.Bold)
                    OutlinedButton(
                        onClick = { showEndTimePicker = true },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(state.endTime, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar")
                }
                Button(
                    onClick = { viewModel.saveSchedule() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Crear")
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        // --- Dialogos de Tiempo ---
        if (showStartTimePicker) {
            WheelTimePickerDialog(
                initialTime = state.startTime,
                onDismiss = { showStartTimePicker = false },
                onTimeSelected = { viewModel.updateStartTime(it); showStartTimePicker = false }
            )
        }

        if (showEndTimePicker) {
            WheelTimePickerDialog(
                initialTime = state.endTime,
                onDismiss = { showEndTimePicker = false },
                onTimeSelected = { viewModel.updateEndTime(it); showEndTimePicker = false }
            )
        }
    }
}