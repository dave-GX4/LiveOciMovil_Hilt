package com.updavid.liveoci_hilt.features.analyzer.presentation.pages

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.core.ui.atoms.SectionTitle
import com.updavid.liveoci_hilt.features.analyzer.presentation.components.AppUsageItemCard
import com.updavid.liveoci_hilt.features.analyzer.presentation.components.PermissionCard
import com.updavid.liveoci_hilt.features.analyzer.presentation.viewmodels.AnalyzerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyzerPage(
    viewModel: AnalyzerViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Launcher para abrir los ajustes de Android y detectar cuando el usuario regresa
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        // Cuando el usuario regresa de ajustes, volvemos a intentar cargar
        viewModel.loadUsageStats()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Uso de Apps y Estudio", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { viewModel.loadUsageStats() }) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "Actualizar", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ayúdanos a equilibrar tu dopamina. Revisa tu uso y actualiza tu enfoque académico.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                uiState.requiresPermission -> {
                    PermissionCard(onClick = {
                        settingsLauncher.launch(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                    })
                }

                uiState.errorMessage != null -> {
                    Text(text = uiState.errorMessage!!, color = Color.Red)
                }

                else -> {
                    SectionTitle(text = "Apps más usadas", modifier = Modifier.padding(bottom = 16.dp))

                    if (uiState.appsUsage.isEmpty()) {
                        Text("No hay datos suficientes de uso de hoy.", color = Color.Gray)
                    } else {
                        // Lista animada de apps
                        uiState.appsUsage.forEachIndexed { index, app ->
                            AppUsageItemCard(
                                appInfo = app,
                                maxUsageMillis = uiState.maxUsageMillis,
                                // Pequeño retraso basado en el índice para efecto cascada
                                animationDelay = index * 100
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Aquí iría tu sección de Contexto Académico
                    SectionTitle(text = "Contexto Académico", modifier = Modifier.padding(bottom = 16.dp))
                    // ... [Tus Chips de materias irían aquí] ...

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { /* Acción para analizar */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Analizar Balance", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                    }
                }
            }

            // Efecto Spotify: Espacio vital para la barra de navegación global
            Spacer(modifier = Modifier.height(130.dp))
        }
    }
}