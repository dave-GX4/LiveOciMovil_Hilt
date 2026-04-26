package com.updavid.liveoci_hilt.features.friends.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.friends.domain.usecases.friend.FriendUseCases
import com.updavid.liveoci_hilt.features.friends.presentation.page.FriendUiSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendUseCases: FriendUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(FriendUiSate())
    val uiState = _uiState.asStateFlow()

    init {
        loadFriends()
    }

    fun loadFriends() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }
            try {
                val friendsList = friendUseCases.getAllFriends()
                _uiState.update { it.copy(isLoading = false, friends = friendsList) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, isError = e.message) }
            }
        }
    }

    fun deleteFriend(friendshipId: String) {
        viewModelScope.launch {
            try {
                val response = friendUseCases.deleteFriend(friendshipId)
                _uiState.update { it.copy(isSuccess = response.message) }
                loadFriends()
            } catch (e: Exception) {
                _uiState.update { it.copy(isError = e.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, isSuccess = null) }
    }
}