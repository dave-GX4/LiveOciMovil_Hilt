package com.updavid.liveoci_hilt.features.bored.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.core.ui.states.FilterType
import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.bored.domain.entity.CategoryModel
import com.updavid.liveoci_hilt.features.bored.domain.usecases.BoredActivitiesUseCases
import com.updavid.liveoci_hilt.features.bored.presentation.page.BoredActivityUiState
import com.updavid.liveoci_hilt.features.leisure.domain.usecases.GeminiUseCases
import com.updavid.liveoci_hilt.features.leisure.domain.usecases.GenerateActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoredActivityViewModel @Inject constructor(
    private val boredActivitiesUseCases: BoredActivitiesUseCases,
    private val geminiUseCases: GeminiUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(BoredActivityUiState())
    val uiState = _uiState.asStateFlow()

    val categories = listOf(
        CategoryModel("Educación", "education"),
        CategoryModel("Recreacional", "recreational"),
        CategoryModel("Social", "social"),
        CategoryModel("Benéfico", "charity"),
        CategoryModel("Cocina", "cooking"),
        CategoryModel("Relajación", "relaxation"),
        CategoryModel("Sin sentido", "busywork")
    )

    init {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = categories.first())
        }

        loadActivitiesBored()
    }

    fun loadActivitiesBored() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }

            val currentCategoryApiName = _uiState.value.selectedCategory?.apiName
            val currentParticipants = _uiState.value.selectedParticipants
            val result = boredActivitiesUseCases.getFilterActivities(
                type = currentCategoryApiName,
                participants = currentParticipants
            )

            result.onSuccess { list ->
                _uiState.update {
                    it.copy(isLoading = false, activities = list)
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(isLoading = false, isError = error.message)
                }
            }
        }
    }

    fun toggleFilter(filter: FilterType) {
        _uiState.update {
            it.copy(activeFilter = if (it.activeFilter == filter) FilterType.NONE else filter)
        }
    }

    fun onCategorySelected(category: CategoryModel) {
        _uiState.update { it.copy(selectedCategory = category) }

        loadActivitiesBored()
    }

    fun onParticipantsSelected(participants: Int?) {
        _uiState.update { it.copy(selectedParticipants = participants) }

        loadActivitiesBored()
    }

    fun onGenerateActivityClicked(activity: BoredActivity) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null, successMessage = null) }

            val result = geminiUseCases.generateActivity(activity)

            result.onSuccess { geminiMessage ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = "¡${geminiMessage.message}!"
                    )
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, isError = error.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(isError = null, successMessage = null) }
    }
}