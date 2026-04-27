package com.updavid.liveoci_hilt.features.activity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.activity.domain.usecases.ActivityUseCases
import com.updavid.liveoci_hilt.features.activity.presentation.page.ActivitiesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val activityUseCases: ActivityUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(ActivitiesUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        observeLocalActivities()
        syncRemoteActivities()
    }

    private fun observeLocalActivities() {
        viewModelScope.launch {
            activityUseCases.getAllActivitiesRoom().collect { records ->
                _uiState.update { currentState ->
                    currentState.copy(
                        leisureRecord = records
                    )
                }
            }
        }
    }

    fun syncRemoteActivities() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }

            val result = activityUseCases.getAllActivitiesRemote()

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(
                        isLoading = false,
                        isSuccess = "Actividades sincronizadas correctamente"
                    ) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        isError = error.message ?: "Error al sincronizar datos"
                    ) }
                }
            )
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, isSuccess = null) }
    }
}