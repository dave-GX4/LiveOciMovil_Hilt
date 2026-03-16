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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class FormScheduleViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(FormScheduleUiState())
    val uiState = _uiState.asStateFlow()

    private val titleRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun onTitleChanged(title: String) {
        val error = if (title.isBlank()) {
            "El título no puede estar vacío"
        } else if (!title.matches(titleRegex)) {
            "El título solo debe contener letras"
        } else {
            null
        }

        _uiState.update { it.copy(title = title, titleError = error) }
    }

    fun onDaysChanged(day: Int) {
        val currentDays = _uiState.value.days.toMutableList()
        if (currentDays.contains(day)) {
            currentDays.remove(day)
        } else {
            currentDays.add(day)
        }

        val error = if (currentDays.isEmpty()) {
            "Debes seleccionar al menos un día"
        } else {
            null
        }

        _uiState.update { it.copy(days = currentDays, daysError = error) }
    }

    fun onStartTimeChanged(startTime: String) {
        _uiState.update { it.copy(startTime = startTime) }
        validateTimes(startTime, _uiState.value.endTime)
    }

    fun onEndTimeChanged(endTime: String) {
        _uiState.update { it.copy(endTime = endTime) }
        validateTimes(_uiState.value.startTime, endTime)
    }

    fun onActiveChanged(active: Boolean) {
        _uiState.update { it.copy(isActive = active) }
    }

    private fun validateTimes(start: String, end: String) {
        if (start.isBlank() || end.isBlank()) {
            _uiState.update { it.copy(timeError = null) }
            return
        }

        try {
            val startTimeParsed = LocalTime.parse(start, timeFormatter)
            val endTimeParsed = LocalTime.parse(end, timeFormatter)

            val error = if (endTimeParsed.isBefore(startTimeParsed) || endTimeParsed == startTimeParsed) {
                "La hora de fin debe ser posterior a la de inicio"
            } else {
                null
            }
            _uiState.update { it.copy(timeError = error) }

        } catch (e: DateTimeParseException) {
            _uiState.update { it.copy(timeError = "Formato de hora inválido (usa HH:mm)") }
        }
    }

    private fun isValidForm(): Boolean {
        onTitleChanged(_uiState.value.title)
        validateTimes(_uiState.value.startTime, _uiState.value.endTime)

        val state = _uiState.value
        return state.titleError == null &&
                state.days.isNotEmpty() &&
                state.timeError == null &&
                state.startTime.isNotBlank() &&
                state.endTime.isNotBlank()
    }

    fun onSaveSchedule() {
        if (!isValidForm()) {
            _uiState.update { it.copy(isError = "Revisa los errores en el formulario") }
            return
        }

        val state = _uiState.value
        _uiState.update { it.copy(isLoading = true, isError = null) }

        viewModelScope.launch {
            val result = if (state.id == null) {
                scheduleUseCases.addSchedule(
                    title = state.title.trim(),
                    days = state.days,
                    start_time = state.startTime,
                    end_time = state.endTime,
                    active = state.isActive,
                    type = state.type
                )
            } else {
                val id = state.id
                val title = state.title.trim()
                val days = state.days
                val start_time = state.startTime
                val end_time = state.endTime
                val active = state.isActive

                scheduleUseCases.updateSchedule(
                    id,
                    title,
                    days,
                    start_time,
                    end_time,
                    active
                )
            }

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { exception ->
                _uiState.update { it.copy(isLoading = false, isError = exception.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(isError = null) }
    }

    fun onResetForm() {
        _uiState.value = FormScheduleUiState()
    }
}