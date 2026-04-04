package com.updavid.liveoci_hilt.features.user.presentation.page

import com.updavid.liveoci_hilt.features.user.domain.entity.User

data class ProfileEditUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val isError: String? = null,
    val isSuccess: String? = null,

    // Inputs
    val newName: String = "",
    val newEmail: String = "",
    val newPass: String = "",
    val confirmationPass: String = "",

    // Errores de validación
    val newNameError: String? = null,
    val newEmailError: String? = null,
    val newPassError: String? = null,
    val confirmationPassError: String? = null,

    // Flags de visibilidad (Edición)
    val editName: Boolean = false,
    val editEmail: Boolean = false,
    val editPass: Boolean = false,
    val showDeleteDialog: Boolean = false
)
