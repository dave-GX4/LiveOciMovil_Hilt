package com.updavid.liveoci_hilt.features.home.presentation.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.bored.domain.usecases.BoredActivitiesUseCases
import com.updavid.liveoci_hilt.features.home.presentation.page.HomeUiState
import com.updavid.liveoci_hilt.features.user.domain.usescases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val boredActivitiesUseCases: BoredActivitiesUseCases,
    private val userUseCases: UserUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        calculateGreeting()
        observeUser()
        fetchRecommendedActivity()
    }

    private fun calculateGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val (message, icon) = when (hour) {
            in 6..11 -> "Buenos días," to Icons.Default.WbSunny
            in 12..18 -> "Buenas tardes," to Icons.Default.WbTwilight
            else -> "Buenas noches," to Icons.Default.NightsStay
        }

        _uiState.update {
            it.copy(
                greeting = message,
                greetingIcon = icon
            )
        }
    }

    private fun observeUser() {
        viewModelScope.launch {
            userUseCases.getUserRoom().collect { user ->
                _uiState.update { it.copy(userName = user?.name) }
            }
        }
    }

    fun fetchRecommendedActivity() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }

            val result = boredActivitiesUseCases.getRandomActivity()

            result.onSuccess { activity ->
                _uiState.update {
                    it.copy(isLoading = false, recommendedActivity = activity)
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(isLoading = false, isError = error.message)
                }
            }
        }
    }
}