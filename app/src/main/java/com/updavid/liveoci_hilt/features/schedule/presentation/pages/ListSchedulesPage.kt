package com.updavid.liveoci_hilt.features.schedule.presentation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.features.schedule.presentation.components.ScheduleCard
import com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels.ListSchedulesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSchedulesPage(
    viewModel: ListSchedulesViewModel,
    onFormAdd: () -> Unit,
    onBack: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val workSchedules = uiState.schedules.filter { it.type.uppercase() == "TRABAJO" }
    val specialSchedules = uiState.schedules.filter { it.type.uppercase() != "TRABAJO" }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF9F9F9),
        topBar = {
            TopAppBar(
                title = { Text("Mis Horarios", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = onFormAdd) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Agregar",
                            tint = Color(0xFF00E676),
                            modifier = Modifier
                                .background(Color(0xFFE8F5E9), CircleShape)
                                .padding(4.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // El PullToRefresh envuelve toda la lista
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.downloadSchedulesManual() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                if (uiState.schedules.isEmpty() && !uiState.isLoading && !uiState.isRefreshing) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No tienes horarios configurados.\n¡Desliza para refrescar o añade uno nuevo!",
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    if (workSchedules.isNotEmpty()) {
                        item {
                            Text(
                                text = "HORARIO DE TRABAJO",
                                color = Color.Gray,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                            )
                        }
                        items(workSchedules, key = { it.id }) { schedule ->
                            ScheduleCard(
                                schedule = schedule,
                                onEdit = { /* Navegar a Form pasando el ID */ },
                                onDelete = { /* Llamar al ViewModel para borrar */ },
                                onToggleActive = { /* Llamar al ViewModel para cambiar estado */ }
                            )
                        }
                    }

                    if (specialSchedules.isNotEmpty()) {
                        item {
                            Text(
                                text = "HORARIOS ESPECIALES",
                                color = Color.Gray,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 8.dp, top = 24.dp)
                            )
                        }
                        items(specialSchedules, key = { it.id }) { schedule ->
                            ScheduleCard(
                                schedule = schedule,
                                onEdit = { /* ... */ },
                                onDelete = { /* ... */ },
                                onToggleActive = { /* ... */ }
                            )
                        }
                    }
                }
            }
        }
    }
}