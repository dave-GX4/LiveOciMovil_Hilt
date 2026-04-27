package com.updavid.liveoci_hilt.features.activity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord
import com.updavid.liveoci_hilt.features.activity.domain.entity.UpdateLeisureRequest
import com.updavid.liveoci_hilt.features.activity.domain.usecases.ActivityUseCases
import com.updavid.liveoci_hilt.features.activity.presentation.page.ActivitiesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val activityUseCases: ActivityUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(ActivitiesUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _activeTimers = MutableStateFlow<Map<String, Long>>(emptyMap())
    private val _showRatingModal = MutableStateFlow<LeisureRecord?>(null)
    val showRatingModal = _showRatingModal.asStateFlow()

    init {
        observeLocalActivities()
        syncRemoteActivities()
    }

    private fun observeLocalActivities() {
        viewModelScope.launch {
            activityUseCases.getAllActivitiesRoom().collect { records ->
                _uiState.update { currentState ->
                    val availableDates = records.filterNotNull().map { it.createdAt.substringBefore("T") }
                        .distinct().sorted()

                    val newSelectedDate = currentState.selectedDate ?: availableDates.lastOrNull()

                    currentState.copy(
                        leisureRecord = records,
                        selectedDate = newSelectedDate
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
                        isLoading = false
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

    fun deleteActivity(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null, isSuccess = null) }

            val result = activityUseCases.deleteActivity(id)

            result.fold(
                onSuccess = { response ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        isSuccess = response.message
                    ) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        isError = error.message ?: "Error al eliminar la actividad"
                    ) }
                }
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onDateSelected(date: String?) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun setActivityToDelete(id: String?) {
        _uiState.update { it.copy(activityToDelete = id) }
    }

    fun startTimer(leisureId: String) {
        val startTime = System.currentTimeMillis()
        _activeTimers.update { it + (leisureId to startTime) }
    }

    fun stopTimer(leisureId: String, record: LeisureRecord) {
        val startTime = _activeTimers.value[leisureId] ?: return
        val endTime = System.currentTimeMillis()

        val startStr = formatMillisToTime(startTime)
        val endStr = formatMillisToTime(endTime)

        _activeTimers.update { it - leisureId }

        _showRatingModal.value = record.copy(startTime = startStr, endTime = endStr)
    }

    fun completeActivity(id: String, satisfaction: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null, isSuccess = null) }

            try {
                val record = _uiState.value.leisureRecord.find { it?.id == id }
                    ?: throw Exception("Actividad no encontrada")

                val currentTime = LocalTime.now()
                val currentDate = LocalDate.now()

                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val endTimeStr = currentTime.format(formatter)

                val durationMins = record.activity.durationMinutes.toLong()
                val startTimeStr = currentTime.minusMinutes(durationMins).format(formatter)

                val request = UpdateLeisureRequest(
                    scheduleDate = currentDate.toString(),
                    startTime = startTimeStr,
                    endTime = endTimeStr,
                    satisfaction = satisfaction,
                    status = "completado"
                )

                val result = activityUseCases.updateLeisure(id, request)

                result.fold(
                    onSuccess = { response ->
                        _uiState.update { it.copy(
                            isLoading = false,
                            isSuccess = "¡Actividad completada, buen trabajo!"
                        ) }

                        syncRemoteActivities()
                    },
                    onFailure = { error ->
                        _uiState.update { it.copy(
                            isLoading = false,
                            isError = error.message ?: "Error al guardar tu calificación"
                        ) }
                    }
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, isError = e.message) }
            }
        }
    }

    private fun formatMillisToTime(millis: Long): String {
        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
        return String.format("%02d:%02d", date.hour, date.minute)
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, isSuccess = null) }
    }

    fun clearRatingModal() {
        _showRatingModal.value = null
    }
}