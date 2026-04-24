package com.updavid.liveoci_hilt.features.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UserUseCases
import com.updavid.liveoci_hilt.features.user.presentation.page.ProfileEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val useCases: UserUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUserRoom()
    }

    private fun observeUserRoom() {
        viewModelScope.launch {
            useCases.getUserRoom().collect { user ->
                _uiState.update { it.copy(user = user) }
            }
        }
    }

    fun toggleEdit(field: String, active: Boolean) {
        _uiState.update {
            when (field) {
                "name" -> it.copy(editName = active, newName = "", newNameError = null)
                "email" -> it.copy(editEmail = active, newEmail = "", newEmailError = null)
                "pass" -> it.copy(editPass = active, newPass = "", confirmationPass = "", newPassError = null)
                "delete" -> it.copy(showDeleteDialog = active)
                else -> it
            }
        }
    }

    fun onNameChanged(name: String) {
        val hasUpperCase = name.any { it.isUpperCase() }
        val hasNoNumbers = name.none { it.isDigit() }
        val error = when {
            name.isEmpty() -> null
            !hasUpperCase -> "Falta mayúscula"
            !hasNoNumbers -> "No debe tener números"
            else -> null
        }
        _uiState.update { it.copy(newName = name, newNameError = error) }
    }

    fun onEmailChanged(email: String) {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val error = if (email.isNotEmpty() && !isValid) "Email inválido" else null
        _uiState.update { it.copy(newEmail = email, newEmailError = error) }
    }

    fun onPasswordChanged(password: String) {
        val error = when {
            password.isEmpty() -> null
            password.length < 8 -> "Mínimo 8 caracteres"
            password.none { it.isUpperCase() } -> "Falta mayúscula"
            password.none { it.isDigit() } -> "Falta número"
            else -> null
        }
        _uiState.update { it.copy(newPass = password, newPassError = error) }
    }

    fun onConfirmationChanged(confirmation: String) {
        val error = if (_uiState.value.newPass != confirmation) "No coinciden" else null
        _uiState.update { it.copy(confirmationPass = confirmation, confirmationPassError = error) }
    }

    fun updateName() {
        if (_uiState.value.newNameError != null || _uiState.value.newName.isBlank()) return
        performUpdate { useCases.updateNameUserUseCase(_uiState.value.newName) }
    }

    fun updateEmail() {
        if (_uiState.value.newEmailError != null || _uiState.value.newEmail.isBlank()) return
        performUpdate { useCases.updateEmailUserUseCase(_uiState.value.newEmail) }
    }

    fun updatePassword() {
        if (_uiState.value.newPassError != null || _uiState.value.confirmationPassError != null) return
        performUpdate { useCases.updatePasswordUserUseCase(_uiState.value.newPass) }
    }

    fun onDeleteAccount() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, showDeleteDialog = false) }

            useCases.deleteAccountUser()
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = "Cuenta eliminada") }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, isError = error.message) }
                }
        }
    }

    private fun performUpdate(call: suspend () -> Result<Message>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null, isSuccess = null) }
            call().onSuccess { message ->
                _uiState.update {
                    it.copy(isLoading = false, isSuccess = message.message, editName = false, editEmail = false, editPass = false)
                }
                useCases.getUserByIdRemoteUseCase()
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, isError = error.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, isSuccess = null) }
    }
}