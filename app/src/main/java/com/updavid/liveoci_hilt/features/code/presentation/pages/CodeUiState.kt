package com.updavid.liveoci_hilt.features.code.presentation.pages

import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser

data class CodeUiState(
    val isLoading: Boolean = true,
    val codeData: Code? = null,
    val errorMessage: String? = null,
    val isInvitationCodeVisible: Boolean = false,
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val foundUser: FoundUser? = null,
    val searchErrorMessage: String? = null
)