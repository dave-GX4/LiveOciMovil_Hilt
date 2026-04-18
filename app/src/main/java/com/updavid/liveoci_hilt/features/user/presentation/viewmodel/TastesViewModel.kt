package com.updavid.liveoci_hilt.features.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.user.domain.usescases.UserUseCases
import com.updavid.liveoci_hilt.features.user.presentation.page.TastesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TastesViewModel @Inject constructor(
    private val useCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(TastesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            useCases.getUserRoom().collect { user ->
                user?.let {
                    _uiState.update { currentState ->

                        val textToRemove = "No has agregado ningun interes"
                        val cleanInterests = it.interests.filter { interest ->
                            interest != textToRemove
                        }

                        val updatedAvailableInterests = (currentState.availableInterests + cleanInterests).distinct()

                        currentState.copy(
                            interests = cleanInterests,
                            topics = it.topics,
                            description = it.description,
                            availableInterests = updatedAvailableInterests
                        )
                    }
                }
            }
        }
    }

    fun onDescriptionChanged(newDescription: String) {
        _uiState.update { it.copy(description = newDescription, isChanged = true) }
    }

    fun onToggleInterest(interest: String) {
        _uiState.update { currentState ->
            val newList = if (currentState.interests.contains(interest)) {
                currentState.interests - interest
            } else {
                currentState.interests + interest
            }
            currentState.copy(interests = newList, isChanged = true)
        }
    }

    fun onAddCustomInterest(newInterest: String) {
        val trimmed = newInterest.trim()
        if (trimmed.isNotBlank()) {
            _uiState.update { currentState ->
                val newAvailable = if (!currentState.availableInterests.contains(trimmed)) {
                    currentState.availableInterests + trimmed
                } else {
                    currentState.availableInterests
                }

                val newSelected = if (!currentState.interests.contains(trimmed)) {
                    currentState.interests + trimmed
                } else {
                    currentState.interests
                }

                currentState.copy(
                    availableInterests = newAvailable,
                    interests = newSelected,
                    isChanged = true
                )
            }
        }
    }

    fun onRemoveCustomInterest(interestToRemove: String) {
        _uiState.update { currentState ->
            if (!currentState.defaultInterests.contains(interestToRemove)) {
                currentState.copy(
                    availableInterests = currentState.availableInterests - interestToRemove,
                    interests = currentState.interests - interestToRemove,
                    isChanged = true
                )
            } else {
                currentState
            }
        }
    }

    fun onAddTopic(newTopic: String) {
        if (newTopic.isNotBlank()) {
            _uiState.update { currentState ->
                currentState.copy(
                    topics = currentState.topics + newTopic.trim(),
                    isChanged = true
                )
            }
        }
    }

    fun onRemoveTopic(topicToRemove: String) {
        _uiState.update { currentState ->
            currentState.copy(
                topics = currentState.topics - topicToRemove,
                isChanged = true
            )
        }
    }

    fun enableEditMode() {
        _uiState.update { it.copy(isEditing = true) }
    }

    fun cancelEditMode() {
        loadUserData()
        _uiState.update { it.copy(isEditing = false, isChanged = false) }
    }

    fun saveTastes() {
        val state = _uiState.value
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = useCases.updateTastesUserUseCase(
                interests = state.interests,
                topics = state.topics,
                description = state.description
            )

            result.onSuccess { message ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = "¡Guardado con éxito!",
                        isChanged = false,
                        isEditing = false
                    )
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
            }
        }
    }
}