package com.updavid.liveoci_hilt.features.schedule.presentation.pages

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.core.ui.atoms.SwitchCard
import com.updavid.liveoci_hilt.core.ui.atoms.TextFieldComponent
import com.updavid.liveoci_hilt.features.schedule.presentation.components.DaySelectorComponent
import com.updavid.liveoci_hilt.features.schedule.presentation.components.TimeRangeSelector
import com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels.FormScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormSchedulePage(
    viewModel: FormScheduleViewModel,
    onBack: () -> Unit,
    onSuccessSaved: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    BackHandler {
        viewModel.onResetForm()
        onBack()
    }

    LaunchedEffect(uiState.isError) {
        uiState.isError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "Horario guardado correctamente", Toast.LENGTH_SHORT).show()
            viewModel.onResetForm()
            onSuccessSaved()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Agregar Horario Especial", fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.onResetForm()
                            onBack()
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },

            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Button(
                        onClick = viewModel::onSaveSchedule,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00E676),
                            disabledContainerColor = Color(0xFF00E676).copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Guardar Horario", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TextFieldComponent(
                    value = uiState.title,
                    onValueChange = viewModel::onTitleChanged,
                    label = "Título de la actividad",
                    errorMessage = uiState.titleError,
                )

                TimeRangeSelector(
                    startTime = uiState.startTime,
                    endTime = uiState.endTime,
                    onStartTimeSelected = viewModel::onStartTimeChanged,
                    onEndTimeSelected = viewModel::onEndTimeChanged,
                    errorMessage = uiState.timeError
                )

                SwitchCard(
                    title = "¿Activar horario?",
                    subtitle = "Habilitar esta actividad en tu rutina",
                    checked = uiState.isActive,
                    onCheckedChange = viewModel::onActiveChanged,
                    activeColor = Color(0xFF00E676)
                )

                DaySelectorComponent(
                    selectedDays = uiState.days,
                    onDayClick = viewModel::onDaysChanged,
                    errorMessage = uiState.daysError
                )
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(enabled = false, onClick = {}),
                contentAlignment = Alignment.Center
            ) {}
        }
    }
}