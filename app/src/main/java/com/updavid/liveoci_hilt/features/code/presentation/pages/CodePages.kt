package com.updavid.liveoci_hilt.features.code.presentation.pages

import com.updavid.liveoci_hilt.features.code.presentation.viewmodels.CodeViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.features.code.presentation.components.InvitationCodeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodePage(
    viewModel: CodeViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Regresar",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Codigo de Amistad",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        color = Color(0xFFAEBBFF),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = "Error: ${uiState.errorMessage}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                uiState.codeData != null -> {
                    InvitationCodeCard(
                        codeData = uiState.codeData!!,
                        isCodeVisible = uiState.isInvitationCodeVisible,
                        onToggleVisibility = viewModel::toggleInvitationCodeVisibility
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}