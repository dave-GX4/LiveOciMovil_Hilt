package com.updavid.liveoci_hilt.features.auth.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.auth.domain.usecases.AuthUseCases
import com.updavid.liveoci_hilt.features.auth.presentation.pages.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
): ViewModel(){
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        val hasUpperCase = name.any { it.isUpperCase() }
        val hasNoNumbers = name.none { it.isDigit() }

        val error = when {
            name.isEmpty() -> null
            !hasUpperCase -> "Falta mayúscula"
            !hasNoNumbers -> "No debe tener números"
            else -> null
        }
        _uiState.update {
            it.copy(
                registerName = name,
                registerNameError = error
            )
        }
    }

    fun onEmailChanged(email: String) {
        val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val error = if (email.isNotEmpty() && !isValid) "Email inválido" else null
        _uiState.update {
            it.copy(
                registerEmail = email,
                registerEmailError = error
            )
        }
    }

    fun onPasswordChanged(password: String) {
        val error = when {
            password.isEmpty() -> null
            password.length < 8 -> "Mínimo 8 caracteres"
            password.none { it.isUpperCase() } -> "Falta mayúscula"
            password.none { it.isDigit() } -> "Falta número"
            else -> null
        }

        val confirmError = if (uiState.value.registerConfirmation.isNotEmpty() &&
            uiState.value.registerConfirmation != password) "No coinciden" else null

        _uiState.update {
            it.copy(
                registerPassword = password,
                registerPasswordError = error,
                registerConfirmationError = confirmError
            )
        }
    }

    fun onConfirmationChanged(confirmation: String) {
        val error = if (uiState.value.registerPassword != confirmation) "No coinciden" else null
        _uiState.update {
            it.copy(
                registerConfirmation = confirmation,
                registerConfirmationError = error
            )
        }
    }

    fun onTermsAcceptedUserChanged(isAccepted: Boolean) {
        _uiState.update { it.copy(isTermsAcceptedUser = isAccepted) }
    }

    fun onAuthentication() {
        val state = _uiState.value

        if(state.registerNameError != null || state.registerEmailError != null ||
            state.registerPasswordError != null){
            return
        }

        _uiState.update { it.copy(isLoading = true, authError = null) }

        viewModelScope.launch {
            val name = state.registerName
            val email = state.registerEmail
            val password = state.registerPassword

            val result = authUseCases.register(name, email, password)

            result.onSuccess { authMessage ->
                _uiState.update { RegisterUiState(isRegisterSuccessful = true) }

            }.onFailure { exception ->
                val errorMessage = exception.message ?: "Ocurrió un error desconocido"
                _uiState.update { it.copy(isLoading = false, authError = errorMessage) }
            }
        }
    }

    fun clearAuthError() {
        _uiState.update { it.copy(authError = null) }
    }
}