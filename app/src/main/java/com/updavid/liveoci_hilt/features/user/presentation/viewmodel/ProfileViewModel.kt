package com.updavid.liveoci_hilt.features.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.user.domain.usescases.UserUseCases
import com.updavid.liveoci_hilt.features.user.presentation.page.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUserRoom()
    }

    private fun observeUserRoom() {
        viewModelScope.launch {
            userUseCases.getUserRoom().collect { user ->
                _uiState.update { it.copy(user = user) }
            }
        }
    }

    fun refreshUserRemote() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefresh = true, isError = null) }

            userUseCases.getUserByIdRemoteUseCase().onFailure { error ->
                _uiState.update { it.copy(isError = error.message) }
            }

            _uiState.update { it.copy(isRefresh = false) }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            userUseCases.logout().onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = "Sesión cerrada") }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, isError = error.message) }
            }
        }
    }

    fun onNotificationToggled(enabled: Boolean) {
        _uiState.update { it.copy(notificationStatus = enabled) }
    }

    fun onDarkModeToggled(enabled: Boolean) {
        _uiState.update { it.copy(darkModeStatus = enabled) }
    }
}