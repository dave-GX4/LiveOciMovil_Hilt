package com.updavid.liveoci_hilt.features.activity.presentation.page

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.features.activity.presentation.components.CustomDropdownMenu
import com.updavid.liveoci_hilt.features.activity.presentation.components.CustomTextField
import com.updavid.liveoci_hilt.features.activity.presentation.components.SelectableChip
import com.updavid.liveoci_hilt.features.activity.presentation.viewmodel.ActivityFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormActivityPage(
    viewModel: ActivityFormViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetForm()
        }
    }

    LaunchedEffect(uiState.isSuccess, uiState.isError) {
        uiState.isSuccess?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearMessages()
            onBack()
        }
        uiState.isError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearMessages()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Nueva Actividad", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.resetForm()
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            CustomTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = "Nombre de la actividad",
                errorText = uiState.nameError
            )

            CustomTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = "Descripción",
                errorText = uiState.descriptionError,
                singleLine = false,
                modifier = Modifier.height(100.dp)
            )

            Column {
                Text("Categoría", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                CustomDropdownMenu(
                    options = viewModel.categories,
                    selectedValue = uiState.category,
                    onValueChange = viewModel::onCategoryChange,
                    placeholder = "Selecciona una categoría"
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Duración estimada: ${uiState.durationMinutes} min", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                Slider(
                    value = uiState.durationMinutes.toFloat(),
                    onValueChange = { viewModel.onDurationChange(it.toInt()) },
                    valueRange = 5f..240f,
                    steps = 46
                )

                // Botones rápidos (15m, 30m, 1h...)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(viewModel.quickDurations) { duration ->
                        SelectableChip(
                            text = if(duration >= 60) "${duration/60}h" else "${duration}m",
                            isSelected = uiState.durationMinutes == duration,
                            onClick = { viewModel.onDurationChange(duration) }
                        )
                    }
                }
            }

            Column {
                Text("Tipo de Ocio", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                CustomDropdownMenu(
                    options = viewModel.type,
                    selectedValue = uiState.type,
                    onValueChange = viewModel::onTypeChange,
                    placeholder = "Selecciona el tipo (Activo/Pasivo)"
                )
            }

            Column {
                Text("Tipo Social", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    viewModel.socialTypes.forEach { social ->
                        SelectableChip(
                            text = social,
                            isSelected = uiState.socialType == social,
                            onClick = { viewModel.onSocialTypeChange(social) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            uiState.generalError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.submitForm() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !uiState.isLoading && uiState.isFormValid
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Crear Actividad", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}