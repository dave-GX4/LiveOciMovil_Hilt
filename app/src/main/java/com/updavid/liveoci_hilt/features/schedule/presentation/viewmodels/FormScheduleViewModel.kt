package com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.ScheduleUseCases
import com.updavid.liveoci_hilt.features.schedule.presentation.pages.FormScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormScheduleViewModel @Inject constructor(
    private val useCases: ScheduleUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(FormScheduleUiState())
    val state = _state.asStateFlow()

    fun updateTitle(title: String) { _state.update { it.copy(title = title) } }
    fun updateType(type: String) { _state.update { it.copy(type = type) } }
    fun updateStartTime(time: String) { _state.update { it.copy(startTime = time) } }
    fun updateEndTime(time: String) { _state.update { it.copy(endTime = time) } }

    fun toggleDay(day: Int) {
        _state.update { currentState ->
            val currentDays = currentState.days.toMutableList()
            if (currentDays.contains(day)) currentDays.remove(day) else currentDays.add(day)
            currentState.copy(days = currentDays)
        }
    }

    fun clearMessages() {
        _state.update { it.copy(errorMessage = null, successMessage = null) }
    }

    fun saveSchedule() {
        val currentState = _state.value

        // Validaciones básicas
        if (currentState.title.isBlank()) {
            _state.update { it.copy(errorMessage = "El título no puede estar vacío") }
            return
        }
        if (currentState.days.isEmpty()) {
            _state.update { it.copy(errorMessage = "Selecciona al menos un día") }
            return
        }

        // Validación de horas (24hrs)
        if (!isTimeValid(currentState.startTime, currentState.endTime)) {
            _state.update { it.copy(errorMessage = "La hora de fin debe ser posterior a la de inicio") }
            return
        }

        // Guardar en el servidor y BD
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = useCases.addSchedule(
                title = currentState.title,
                days = currentState.days,
                startTime = currentState.startTime,
                endTime = currentState.endTime,
                active = true,
                type = currentState.type
            )

            result.fold(
                onSuccess = { response ->
                    // Si es exitoso, mandamos el mensaje que viene del backend
                    _state.update { it.copy(isLoading = false, successMessage = response.message ?: "Horario creado") }
                },
                onFailure = { error ->
                    // Si falla, cachamos la excepción y mostramos el error del servidor (el que parseaste en el repositorio)
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }

    // Lógica matemática para comparar "HH:mm"
    private fun isTimeValid(start: String, end: String): Boolean {
        val (startH, startM) = start.split(":").map { it.toInt() }
        val (endH, endM) = end.split(":").map { it.toInt() }

        val startTotalMinutes = (startH * 60) + startM
        val endTotalMinutes = (endH * 60) + endM

        return endTotalMinutes > startTotalMinutes
    }
}