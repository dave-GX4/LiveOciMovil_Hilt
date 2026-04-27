package com.updavid.liveoci_hilt.features.activity.presentation.page

data class FormActivityUiState(
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val category: String = "",
    val durationMinutes: Int = 30,
    val socialType: String = "",

    val nameError: String? = null,
    val descriptionError: String? = null,
    val generalError: String? = null,

    val isSuccess: String? = null,
    val isError: String? = null,
    val isLoading: Boolean = false,
) {
    val isFormValid: Boolean
        get() = name.isNotBlank() &&
                description.isNotBlank() &&
                category.isNotBlank() &&
                type.isNotBlank() &&
                socialType.isNotBlank() &&
                durationMinutes > 0
}