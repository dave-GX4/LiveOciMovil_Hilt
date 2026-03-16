package com.updavid.liveoci_hilt.features.auth.presentation.pages

data class LoginUiState(
    val isLoading: Boolean = false,
    val authError: String? = null,
    val isLoginSuccessful: Boolean = false,

    val loginEmail: String = "",
    val loginPassword: String = "",
    // Errores de Login
    val loginEmailError: String? = null,
    val loginPasswordError: String? = null,
)
