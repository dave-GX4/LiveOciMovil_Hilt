package com.updavid.liveoci_hilt.features.auth.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.auth.domain.usecases.AuthUseCases
import com.updavid.liveoci_hilt.features.auth.presentation.pages.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
): ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val error = if (email.isNotEmpty() && !isValid) "Email inválido" else null

        _uiState.update {
            it.copy(loginEmail = email, loginEmailError = error)
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

        _uiState.update {
            it.copy(loginPassword = password, loginPasswordError = error)
        }
    }

    fun onAuthentication(){
        val state = _uiState.value

        if(state.loginEmail.isBlank() || state.loginPassword.isBlank()){
            _uiState.update { it.copy(loginEmailError = "Requerido", loginPasswordError = "Requerido") }
            return
        }

        _uiState.update { it.copy(isLoading = true, authError = null) }

        viewModelScope.launch {
            val email = state.loginEmail
            val password = state.loginPassword

            val result = authUseCases.login(email, password)

            _uiState.update { it.copy(isLoading = false) }

            result.onSuccess { authMessage ->
                _uiState.update { LoginUiState(isLoginSuccessful = true) }

            }.onFailure { exception ->
                val errorMessage = exception.message ?: "Ocurrió un error desconocido"
                _uiState.update { it.copy(authError = errorMessage) }
            }
        }
    }

    fun clearAuthError() {
        _uiState.update { it.copy(authError = null) }
    }
}