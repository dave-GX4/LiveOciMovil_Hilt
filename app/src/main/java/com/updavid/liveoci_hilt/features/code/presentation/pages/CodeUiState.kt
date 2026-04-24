package com.updavid.liveoci_hilt.features.code.presentation.pages

import com.updavid.liveoci_hilt.features.code.domain.entity.Code

data class CodeUiState(
    val isLoading: Boolean = true,
    val codeData: Code? = null,
    val errorMessage: String? = null,
    val isInvitationCodeVisible: Boolean = false
)