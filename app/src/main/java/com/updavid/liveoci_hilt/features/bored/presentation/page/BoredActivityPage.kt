package com.updavid.liveoci_hilt.features.bored.presentation.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.features.bored.presentation.components.ActivityItemCard
import com.updavid.liveoci_hilt.features.bored.presentation.components.FilterChipCustom
import com.updavid.liveoci_hilt.features.bored.presentation.viewmodel.BoredActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoredActivityPage(
    viewModel: BoredActivityViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {

            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)) {
                    Text(
                        text = "Descubrir Nuevas",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 36.sp
                    )
                    Text(
                        text = "Actividades",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Aumenta tu dopamina de forma natural con selecciones curadas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            item {
                Text(
                    text = "Categoría",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
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

            item {
                Text(
                    text = "Participantes",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 8.dp)
                )
                val participantOptions = listOf(null, 1, 2, 3, 4, 5, 6, 8)

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(participantOptions) { count ->
                        val isSelected = uiState.selectedParticipants == count
                        val textDisplay = count?.toString() ?: "Todos"

                        FilterChipCustom(
                            text = textDisplay,
                            isSelected = isSelected,
                            onClick = { viewModel.onParticipantsSelected(count) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            when {
                uiState.isLoading -> {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
                uiState.isError != null -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(48.dp))
                            Text("Oops! ${uiState.isError}", color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                            Button(
                                onClick = { viewModel.loadActivitiesBored() },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) { Text("Reintentar", color = MaterialTheme.colorScheme.background) }
                        }
                    }
                }
                uiState.activities.isEmpty() -> {
                    item {
                        Text(
                            text = "No se encontraron actividades con estos filtros.",
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            color = Color.Gray
                        )
                    }
                }
                else -> {
                    items(uiState.activities) { activity ->
                        ActivityItemCard(
                            activity = activity,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}