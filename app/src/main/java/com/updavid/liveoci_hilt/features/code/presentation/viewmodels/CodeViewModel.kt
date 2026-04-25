package com.updavid.liveoci_hilt.features.code.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.code.domain.usecases.CodeUseCases
import com.updavid.liveoci_hilt.features.code.presentation.pages.CodeUiState
import com.updavid.liveoci_hilt.features.friends.domain.usecases.request.FriendRequestUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
    private val codeUseCases: CodeUseCases,
    private val friendRequestUseCases: FriendRequestUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CodeUiState())
    val uiState: StateFlow<CodeUiState> = _uiState.asStateFlow()

    init {
        startListeningToMyCode()
    }

    private fun startListeningToMyCode() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                codeUseCases.streamCodeOfUser()
                    .catch { e ->
                        // Si en caso se cae la conexión SSE
                        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                    }
                    .collect { newCode ->
                        // Cada vez que el server mande un código, esto se actualiza
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                codeData = newCode,
                                errorMessage = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun toggleInvitationCodeVisibility() {
        _uiState.update {
            it.copy(isInvitationCodeVisible = !it.isInvitationCodeVisible)
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query, searchErrorMessage = null) }
    }

    fun searchUser() {
        val query = _uiState.value.searchQuery
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true, searchErrorMessage = null, foundUser = null) }
            try {
                val user = codeUseCases.searchUserByCode(query)
                _uiState.update { it.copy(foundUser = user, isSearching = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSearching = false, searchErrorMessage = e.message) }
            }
        }
    }

    fun sendFriendRequest(userIdB: String) {
        viewModelScope.launch {
            try {
                // Indicamos que algo está cargando (opcional: podrías agregar un isActionLoading al estado)
                val response = friendRequestUseCases.sendFriendRequest(userIdB)

                // Mostrar mensaje de éxito en el Snackbar
                _uiState.update { it.copy(searchErrorMessage = response.message) }

                // Opción rápida: Volver a buscar al usuario para que se actualice la tarjeta
                searchUser()
            } catch (e: Exception) {
                _uiState.update { it.copy(searchErrorMessage = e.message) }
            }
        }
    }

    fun cancelFriendRequest(requestId: String) {
        viewModelScope.launch {
            try {
                val response = friendRequestUseCases.cancelFriendRequest(requestId)
                _uiState.update { it.copy(searchErrorMessage = response.message) }
                searchUser()
            } catch (e: Exception) {
                _uiState.update { it.copy(searchErrorMessage = e.message) }
            }
        }
    }

    fun responseFriendRequest(requestId: String, status: String) {
        viewModelScope.launch {
            try {
                val response = friendRequestUseCases.responseFriendRequest(requestId, status)
                _uiState.update { it.copy(searchErrorMessage = response.message) }
                searchUser()
            } catch (e: Exception) {
                _uiState.update { it.copy(searchErrorMessage = e.message) }
            }
        }
    }

    fun clearSearchError() {
        _uiState.update { it.copy(searchErrorMessage = null) }
    }
}