package com.updavid.liveoci_hilt.features.friends.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.friends.domain.entity.Friend
import com.updavid.liveoci_hilt.features.friends.domain.usecases.friend.FriendUseCases
import com.updavid.liveoci_hilt.features.friends.presentation.page.FriendUiSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendUseCases: FriendUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(FriendUiSate())
    val uiState = _uiState.asStateFlow()

    init {
        loadFriendsAndListen()
    }

    private fun loadFriendsAndListen() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }

            try {
                val initialList = friendUseCases.getAllFriends()
                _uiState.update { it.copy(isLoading = false, friends = initialList) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, isError = "Fallo carga inicial: ${e.message}") }
                return@launch
            }

            friendUseCases.streamFriendUpdates()
                .catch { e ->
                    Log.e("FriendsVM", "SSE desconectado: ${e.message}")
                }
                .collect { event ->
                    val currentList = _uiState.value.friends.toMutableList()

                    when (event.action) {
                        "ADDED" -> {
                            if (event.friend != null && currentList.none { it.friendshipId == event.friendshipId }) {
                                currentList.add(0, event.friend)
                            }
                        }
                        "REMOVED" -> {
                            currentList.removeAll { it.friendshipId == event.friendshipId }
                        }
                    }

                    _uiState.update { it.copy(friends = currentList) }
                }
        }
    }

    fun deleteFriend(friendshipId: String) {
        viewModelScope.launch {
            try {
                val updatedList = _uiState.value.friends.filter { it.friendshipId != friendshipId }

                _uiState.update { it.copy(friends = updatedList) }

                val response = friendUseCases.deleteFriend(friendshipId)
                _uiState.update { it.copy(isSuccess = response.message) }

            } catch (e: Exception) {
                // Si por alguna razón la API falla, mostramos el error.
                loadFriendsAndListen()
                _uiState.update { it.copy(isError = e.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, isSuccess = null) }
    }
}