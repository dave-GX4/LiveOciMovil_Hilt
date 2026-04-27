package com.updavid.liveoci_hilt.features.activity.presentation.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val records = uiState.leisureRecord.filterNotNull()
    val availableDates = remember(records) {
        records.map { it.createdAt.substringBefore("T") }
            .distinct()
            .sorted()
    }
    var selectedDate by remember(availableDates) {
        mutableStateOf(availableDates.lastOrNull())
    }
    var searchQuery by remember { mutableStateOf("") }

    val filteredRecords = remember(records, selectedDate, searchQuery) {
        records.filter { record ->
            val recordDate = record.createdAt.substringBefore("T")
            val matchesDate = recordDate == selectedDate
            val matchesSearch = record.activity.name.contains(searchQuery, ignoreCase = true)
            matchesDate && matchesSearch
        }
    }

    val activeRecords = filteredRecords.filter { it.status.lowercase() != "completado" }
    val completedRecords = filteredRecords.filter { it.status.lowercase() == "completado" }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.isError) {
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
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text("Mis Actividades", fontWeight = FontWeight.Bold) },
                actions = {
                    // Botón movido aquí arriba
                    IconButton(onClick = { onNavigateToForm() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Actividad"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("Buscar actividad...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            if (availableDates.isNotEmpty()) {
                DateSelectorRow(
                    dates = availableDates,
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 40.dp)
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
                            isLastItem = index == activeRecords.lastIndex,
                            onDeleteClick = { id ->
                                // viewModel.deleteActivity(id)
                            },
                            onRepeatClick = { },
                            onCompleteClick = { id ->
                                // viewModel.markAsCompleted(id)
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
                            isLastItem = index == completedRecords.lastIndex,
                            onDeleteClick = { id ->
                                // viewModel.deleteActivity(id)
                            },
                            onRepeatClick = { id ->
                                // viewModel.repeatActivity(id)
                            },
                            onCompleteClick = { }
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
    }
}