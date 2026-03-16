package com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.schedule.domain.usecases.ScheduleUseCases
import com.updavid.liveoci_hilt.features.schedule.presentation.pages.ListSchedulesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSchedulesViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(ListSchedulesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadSchedulesRoom()
    }

    private fun loadSchedulesRoom() {
        scheduleUseCases.getSchedulesRoom()
            .onEach { list ->
                _uiState.update {
                    it.copy(
                        schedules = list,
                        isLoading = false,
                        isError = null
                    )
                }
            }.catch { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = exception.message ?: "Error desconocido"
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun downloadSchedulesManual(){
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                scheduleUseCases.getSchedulesRemote()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
}