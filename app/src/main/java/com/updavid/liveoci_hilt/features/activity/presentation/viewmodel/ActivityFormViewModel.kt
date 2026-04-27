package com.updavid.liveoci_hilt.features.activity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.activity.domain.usecases.ActivityUseCases
import com.updavid.liveoci_hilt.features.activity.presentation.page.FormActivityUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityFormViewModel @Inject constructor(
    private val activityUseCases: ActivityUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(FormActivityUiState())
    val uiState = _uiState.asStateFlow()

    val categories = listOf("Deportes", "Arte", "Relajación", "Aprendizaje", "Entretenimiento", "Social", "Salud", "Naturaleza")
    val type = listOf("Activo", "Pasivo")
    val socialTypes = listOf("Solo", "Pareja", "Grupo")
    val quickDurations = listOf(15, 30, 45, 60, 90, 120)

    fun onNameChange(newValue: String) = _uiState.update { it.copy(name = newValue, nameError = null) }
    fun onDescriptionChange(newValue: String) = _uiState.update { it.copy(description = newValue, descriptionError = null) }
    fun onTypeChange(newValue: String) = _uiState.update { it.copy(type = newValue, generalError = null) }
    fun onCategoryChange(newValue: String) = _uiState.update { it.copy(category = newValue, generalError = null) }
    fun onDurationChange(newValue: Int) = _uiState.update { it.copy(durationMinutes = newValue) }
    fun onSocialTypeChange(newValue: String) = _uiState.update { it.copy(socialType = newValue, generalError = null) }

    fun submitForm() {
        val currentState = _uiState.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null, isSuccess = null) }

            val result = activityUseCases.createActivity(
                name = currentState.name,
                description = currentState.description,
                type = currentState.type,
                category = currentState.category,
                durationMinutes = currentState.durationMinutes,
                socialType = currentState.socialType
            )

            result.fold(
                onSuccess = { response ->
                    activityUseCases.getAllActivitiesRemote()
                    _uiState.update { it.copy(isLoading = false, isSuccess = response.message) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, isError = error.message) }
                }
            )
        }
    }

    fun clearMessages() = _uiState.update { it.copy(isSuccess = null, isError = null) }

    fun resetForm() {
        _uiState.value = FormActivityUiState()
    }
}