package com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.core.ui.states.StatusFilter
import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.ScheduleUseCases
import com.updavid.liveoci_hilt.features.schedule.presentation.pages.ScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulesViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _selectedDay = MutableStateFlow<Int?>(null)
    private val _statusFilter = MutableStateFlow(StatusFilter.ALL)

    init {
        loadSchedules()
        syncRemoteSchedules()
    }

    private fun loadSchedules() {
        combine(
            scheduleUseCases.getSchedulesRoom(),
            _selectedDay,
            _statusFilter
        ) { schedules, day, status ->
            var filtered = if (day != null) {
                schedules.filter { it.days.contains(day) }
            } else schedules

            filtered = when (status) {
                StatusFilter.ACTIVE -> filtered.filter { it.active }
                StatusFilter.INACTIVE -> filtered.filter { !it.active }
                StatusFilter.ALL -> filtered
            }

            val (primary, special) = filtered.partition {
                it.type == "trabajo" || it.type == "escuela"
            }

            _uiState.update { currentState ->
                currentState.copy(
                    primarySchedules = primary,
                    specialSchedules = special,
                    selectedDay = day,
                    statusFilter = status,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun syncRemoteSchedules() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                scheduleUseCases.getSchedulesRemote()
            } catch (e: Exception) {
                _uiState.update { it.copy(isError = e.message) }
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun setDayFilter(day: Int?) { _selectedDay.value = day }
    fun setStatusFilter(status: StatusFilter) { _statusFilter.value = status }
    fun setEditingScheduleId(uuid: String?) {
        _uiState.update { it.copy(editingScheduleId = uuid) }
    }

    fun updateSchedule(schedule: Schedule) {
        viewModelScope.launch {
            val result = scheduleUseCases.updateSchedule(
                id = schedule.uuid,
                title = schedule.title,
                days = schedule.days,
                startTime = schedule.startTime,
                endTime = schedule.endTime,
                active = schedule.active
            )

            result.onSuccess { response ->
                _uiState.update { it.copy(isSuccess = response.message) }
            }.onFailure { exception ->
                _uiState.update { it.copy(isError = exception.message ?: "Error al actualizar el horario") }
            }
        }
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch {
            val result = scheduleUseCases.deleteSchedule(schedule.uuid)

            result.onSuccess { response ->
                _uiState.update { it.copy(isSuccess = "Horario eliminado") }
            }.onFailure { exception ->
                _uiState.update { it.copy(isError = exception.message ?: "Error al eliminar el horario") }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, isSuccess = null) }
    }
}