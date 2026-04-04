package com.updavid.liveoci_hilt.features.bored.presentation.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.core.ui.states.FilterType
import com.updavid.liveoci_hilt.features.bored.presentation.components.ActivityItemCard
import com.updavid.liveoci_hilt.features.bored.presentation.components.FilterChipCustom
import com.updavid.liveoci_hilt.features.bored.presentation.components.FilterSelectorButton
import com.updavid.liveoci_hilt.features.bored.presentation.viewmodel.BoredActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoredActivityPage(
    viewModel: BoredActivityViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isError, uiState.successMessage) {
        uiState.isError?.let {
            snackbarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
            viewModel.clearMessages()
        }
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Actividades", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.loadActivitiesBored() },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Recargar")
            }
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Descubrir Nuevas\n")
                            withStyle(style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.ExtraBold
                            )
                            ) {
                                append("Actividades")
                            }
                        },
                        fontSize = 32.sp,
                        lineHeight = 36.sp, // leading-[1.1] aprox
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp, // tracking-tight
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            item {
                Text(
                    text = "Filtrar por",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterSelectorButton(
                        text = "Categoría",
                        icon = Icons.Default.List,
                        isActive = uiState.activeFilter == FilterType.CATEGORY,
                        onClick = { viewModel.toggleFilter(FilterType.CATEGORY) }
                    )
                    FilterSelectorButton(
                        text = "Participantes",
                        icon = Icons.Default.Person,
                        isActive = uiState.activeFilter == FilterType.PARTICIPANTS,
                        onClick = { viewModel.toggleFilter(FilterType.PARTICIPANTS) }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                AnimatedVisibility(visible = uiState.activeFilter == FilterType.CATEGORY) {
                    Column {
                        Text(text = "Selecciona Categoría", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 24.dp))
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(viewModel.categories) { category ->
                                val isSelected = uiState.selectedCategory?.apiName == category.apiName
                                FilterChipCustom(
                                    text = category.nameEs,
                                    isSelected = isSelected,
                                    onClick = { viewModel.onCategorySelected(category) }
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = uiState.activeFilter == FilterType.PARTICIPANTS) {
                    Column {
                        Text(text = "Número de personas", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 24.dp))
                        val participantOptions = listOf(null, 1, 2, 3, 4, 5, 6, 8)
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(participantOptions) { count ->
                                val isSelected = uiState.selectedParticipants == count
                                FilterChipCustom(
                                    text = count?.toString() ?: "Todos",
                                    isSelected = isSelected,
                                    onClick = { viewModel.onParticipantsSelected(count) }
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.activities.isEmpty() && !uiState.isLoading) {
                item {
                    Text(
                        text = "No se encontraron actividades.",
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        color = Color.Gray
                    )
                }
            } else {
                items(uiState.activities) { activity ->
                    ActivityItemCard(
                        activity = activity,
                        onGenerateClick = { viewModel.onGenerateActivityClicked(it) },
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    )
                }
            }
        }
    }
}