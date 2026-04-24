package com.updavid.liveoci_hilt.features.user.presentation.page

import com.updavid.liveoci_hilt.features.user.domain.entity.User
import kotlinx.coroutines.flow.Flow

data class TastesUiState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val interests: List<String> = emptyList(),
    val topics: List<String> = emptyList(),
    val defaultInterests: List<String> = listOf("Música", "Deporte", "Lectura", "Arte", "Naturaleza", "Meditación", "Cocina"),
    val availableInterests: List<String> = listOf("Música", "Deporte", "Lectura", "Arte", "Naturaleza", "Meditación", "Cocina"),
    val description: String = "",
    val isChanged: Boolean = false,
    val isEditing: Boolean = false
)