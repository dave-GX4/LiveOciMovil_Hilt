package com.updavid.liveoci_hilt.features.code.presentation.pages

import CodeViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.updavid.liveoci_hilt.features.code.presentation.components.InvitationCodeCard


@Composable
fun CodePage(
    viewModel: CodeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1117))
            .padding(horizontal = 24.dp)
    ) {
        // Aquí va tu header actual:
        // foto, nombre, email, status...

        Spacer(modifier = Modifier.height(28.dp))

        InvitationCodeCard(
            invitationCode = uiState.invitationCode,
            isCodeVisible = uiState.isInvitationCodeVisible,
            onToggleVisibility = viewModel::toggleInvitationCodeVisibility
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Aquí siguen tus opciones:
        // Mis Gustos, Mis Horarios, Administrar Usuario...
    }
}