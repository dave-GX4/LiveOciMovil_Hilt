package com.updavid.liveoci_hilt.features.analyzer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.analyzer.domain.usecases.GetDailyAppUsageUseCase
import com.updavid.liveoci_hilt.features.analyzer.presentation.pages.AnalyzerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyzerViewModel @Inject constructor(
    private val getDailyAppUsageUseCase: GetDailyAppUsageUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(AnalyzerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUsageStats()
    }

    fun loadUsageStats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = getDailyAppUsageUseCase()

            result.fold(
                onSuccess = { apps ->
                    // Calculamos cuál es la app con más uso para que sea nuestro 100% visual
                    val maxUsage = apps.maxOfOrNull { it.timeSpentMillis } ?: 1L
                    // Filtramos para mostrar solo el top 5 por ejemplo, y apps con más de 1 minuto
                    val topApps = apps.filter { it.timeSpentMillis > 60_000 }.take(5)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            appsUsage = topApps,
                            maxUsageMillis = maxUsage,
                            requiresPermission = false
                        )
                    }
                },
                onFailure = { error ->
                    if (error.message == "Permission denied") {
                        _uiState.update { it.copy(isLoading = false, requiresPermission = true) }
                    } else {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = "Error al cargar datos: ${error.message}")
                        }
                    }
                }
            )
        }
    }
}