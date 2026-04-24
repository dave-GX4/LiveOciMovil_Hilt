package com.updavid.liveoci_hilt.features.schedule.presentation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.core.ui.states.StatusFilter
import com.updavid.liveoci_hilt.features.schedule.presentation.components.DaysFilterRow
import com.updavid.liveoci_hilt.features.schedule.presentation.components.EmptyScheduleState
import com.updavid.liveoci_hilt.features.schedule.presentation.components.TimelineScheduleItem
import com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels.SchedulesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesPage(
    viewModel: SchedulesViewModel,
    onFormAdd: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showConfigMenu by remember { mutableStateOf(false) }
    val allSchedules = remember(state.primarySchedules, state.specialSchedules) {
        (state.primarySchedules + state.specialSchedules).sortedBy { it.startTime }
    }
    val isListEmpty = allSchedules.isEmpty()

    LaunchedEffect(state.isError, state.isSuccess) {
        state.isError?.let { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage)
            viewModel.clearMessages()
        }

        state.isSuccess?.let { successMessage ->
            snackbarHostState.showSnackbar(successMessage)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Horarios") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showConfigMenu = true }) {
                            Icon(Icons.Default.FilterList, "Filtros")
                        }
                        DropdownMenu(
                            expanded = showConfigMenu,
                            onDismissRequest = { showConfigMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Todos") },
                                onClick = { viewModel.setStatusFilter(StatusFilter.ALL); showConfigMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Solo Activos") },
                                onClick = { viewModel.setStatusFilter(StatusFilter.ACTIVE); showConfigMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Solo Inactivos") },
                                onClick = { viewModel.setStatusFilter(StatusFilter.INACTIVE); showConfigMenu = false }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFormAdd) {
                Icon(Icons.Default.Add, "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DaysFilterRow(
                selectedDay = state.selectedDay,
                onDaySelected = { viewModel.setDayFilter(it) }
            )

            if (isListEmpty) {
                EmptyScheduleState()
            } else {
                Box(modifier = Modifier.fillMaxSize()) {

                    LazyColumn(
                        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp, end = 16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(allSchedules) { schedule ->
                            TimelineScheduleItem(
                                schedule = schedule,
                                isLastItem = schedule == allSchedules.last(),
                                isExpanded = state.editingScheduleId == schedule.uuid,
                                onToggleExpand = {
                                    val newId =
                                        if (state.editingScheduleId == schedule.uuid) null else schedule.uuid
                                    viewModel.setEditingScheduleId(newId)
                                },
                                onUpdateSchedule = { updatedSchedule ->
                                    viewModel.updateSchedule(updatedSchedule)
                                },
                                onDelete = {
                                    viewModel.deleteSchedule(schedule)
                                }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}