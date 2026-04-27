package com.updavid.liveoci_hilt.features.friends.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.friends.domain.usecases.request.FriendRequestUseCases
import com.updavid.liveoci_hilt.features.friends.presentation.page.RequestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val friendRequestUseCases: FriendRequestUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRequests()
    }

    fun loadRequests() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }
            try {
                val requests = friendRequestUseCases.getFriendRequest()
                _uiState.update {
                    it.copy(isLoading = false, friendRequests = requests)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, isError = e.message)
                }
            }
        }
    }

    fun respondToRequest(requestId: String, status: String) {
        viewModelScope.launch {
            try {
                val response = friendRequestUseCases.responseFriendRequest(requestId, status)
                _uiState.update { it.copy(isSuccess = response.message) }

                loadRequests()
            } catch (e: Exception) {
                _uiState.update { it.copy(isError = e.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, isSuccess = null) }
    }
}