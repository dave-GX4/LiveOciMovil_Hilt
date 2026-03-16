package com.updavid.liveoci_hilt.features.auth.presentation.pages

data class RegisterUiState(
    val isLoading: Boolean = false,
    val authError: String? = null,
    val isRegisterSuccessful: Boolean = false,
    val isTermsAcceptedUser: Boolean = false,

    val registerName: String = "",
    val registerEmail: String = "",
    val registerPassword: String = "",
    val registerConfirmation: String = "",

    val registerNameError: String? = null,
    val registerEmailError: String? = null,
    val registerPasswordError: String? = null,
    val registerConfirmationError: String? = null,
)
