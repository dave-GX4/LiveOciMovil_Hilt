package com.updavid.liveoci_hilt.features.activity.presentation.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.features.activity.presentation.components.DateSelectorRow
import com.updavid.liveoci_hilt.features.activity.presentation.components.TimelineItem
import com.updavid.liveoci_hilt.features.activity.presentation.viewmodel.ActivitiesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesPage(
    viewModel: ActivitiesViewModel,
    onNavigateToForm: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val ratingModalRecord by viewModel.showRatingModal.collectAsStateWithLifecycle()

    val records = uiState.leisureRecord.filterNotNull()

    val availableDates = remember(records) {
        records.map { it.createdAt.substringBefore("T") }
            .distinct()
            .sorted()
    }

    val filteredRecords = remember(records, uiState.selectedDate, uiState.searchQuery) {
        records.filter { record ->
            val recordDate = record.createdAt.substringBefore("T")
            val matchesDate = recordDate == uiState.selectedDate
            val matchesSearch = record.activity.name.contains(uiState.searchQuery, ignoreCase = true)
            matchesDate && matchesSearch
        }
    }

    val activeRecords = filteredRecords.filter { it.status.lowercase() != "completado" }
    val completedRecords = filteredRecords.filter { it.status.lowercase() == "completado" }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.isSuccess, uiState.isError) {
        uiState.isError?.let { error ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Long,
                    withDismissAction = true
                )
            }
            viewModel.clearMessages()
        }

        uiState.isSuccess?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
            }
            viewModel.clearMessages()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 110.dp)
            )
        },
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Mis Actividades", fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = { onNavigateToForm() }) {
                            Icon(Icons.Default.Add, contentDescription = "Agregar Actividad")
                        }
                    }
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                placeholder = { Text("Buscar actividad...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            if (availableDates.isNotEmpty()) {
                DateSelectorRow(
                    dates = availableDates,
                    selectedDate = uiState.selectedDate,
                    onDateSelected = { viewModel.onDateSelected(it) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                if (activeRecords.isNotEmpty()) {
                    item {
                        Text(
                            text = "Por terminar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp, start = 32.dp)
                        )
                    }
                    itemsIndexed(activeRecords) { index, record ->
                        TimelineItem(
                            record = record,
                            isCompleted = false,
                            isFirstItem = index == 0,
                            isLastItem = index == activeRecords.lastIndex,
                            onDeleteClick = {
                                viewModel.setActivityToDelete(record.id)
                            },
                            onRepeatClick = { },
                            onStartClick = { id ->
                                viewModel.startTimer(id)
                            },
                            onStopClick = { id, rec ->
                                viewModel.stopTimer(id, rec)
                            }
                        )
                    }
                }

                if (completedRecords.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Terminadas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp, start = 32.dp)
                        )
                    }
                    itemsIndexed(completedRecords) { index, record ->
                        TimelineItem(
                            record = record,
                            isCompleted = true,
                            isFirstItem = index == 0,
                            isLastItem = index == completedRecords.lastIndex,
                            onDeleteClick = { id ->
                                viewModel.setActivityToDelete(id)
                            },
                            onRepeatClick = { id ->
                                // viewModel.repeatActivity(id)
                            },
                            onStartClick = { },
                            onStopClick = { _, _ -> }
                        )
                    }
                }

                if (activeRecords.isEmpty() && completedRecords.isEmpty()) {
                    item {
                        Text(
                            text = "No hay actividades para este día.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }
                }
            }
        }

        if (uiState.activityToDelete != null) {
            AlertDialog(
                onDismissRequest = { viewModel.setActivityToDelete(null) },
                title = { Text("Eliminar actividad", fontWeight = FontWeight.Bold) },
                text = { Text("¿Estás seguro de que deseas eliminar esta actividad? Esta acción no se puede deshacer.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteActivity(uiState.activityToDelete!!)
                            viewModel.setActivityToDelete(null)
                        }
                    ) {
                        Text("Eliminar", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.setActivityToDelete(null) }) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.onSurface)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.surface
            )
        }

        if (ratingModalRecord != null) {
            var currentRating by remember { mutableIntStateOf(0) }

            AlertDialog(
                onDismissRequest = { viewModel.clearRatingModal() },
                title = { Text("¡Actividad Terminada!", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("¿Qué tan satisfecho estás con esta actividad?", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            (1..5).forEach { index ->
                                Icon(
                                    imageVector = if (index <= currentRating) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = "Estrella $index",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable { currentRating = index },
                                    tint = if (index <= currentRating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.completeActivity(
                                id = ratingModalRecord!!.id,
                                satisfaction = currentRating
                            )
                            viewModel.clearRatingModal()
                        },
                        enabled = currentRating > 0
                    ) {
                        Text("Guardar", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.clearRatingModal() }) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.onSurface)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}