package com.updavid.liveoci_hilt.features.code.presentation.pages

import com.updavid.liveoci_hilt.features.code.presentation.viewmodels.CodeViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.features.code.presentation.components.FoundUserCard
import com.updavid.liveoci_hilt.features.code.presentation.components.InvitationCodeCard
import com.updavid.liveoci_hilt.features.code.presentation.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodePage(
    viewModel: CodeViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState.searchErrorMessage) {
        uiState.searchErrorMessage?.let { errorMsg ->
            snackbarHostState.showSnackbar(
                message = errorMsg,
                duration = SnackbarDuration.Short
            )
            viewModel.clearSearchError()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 90.dp)
            )
        },
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SectionHeader(
                title = "Mi código de amistad",
                description = "Comparte este código con tus amigos para que puedan agregarte directamente."
            )

            when {
                uiState.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                uiState.codeData != null -> {
                    InvitationCodeCard(
                        codeData = uiState.codeData!!,
                        isCodeVisible = uiState.isInvitationCodeVisible,
                        onToggleVisibility = viewModel::toggleInvitationCodeVisibility
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 32.dp), color = Color.DarkGray)

            SectionHeader(
                title = "Buscar por código",
                description = "Ingresa el código de un amigo para enviarle una solicitud de conexión."
            )

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    viewModel.onSearchQueryChange(it.uppercase())
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: O1KJ-KMWV-PEW", color = Color.Gray) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        viewModel.searchUser()
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            viewModel.searchUser()
                        },
                        enabled = !uiState.isSearching
                    ) {
                        if (uiState.isSearching) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        else Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            uiState.foundUser?.let { user ->
                FoundUserCard(
                    user = user,
                    onAction = { action ->
                        when (action) {
                            "send" -> viewModel.sendFriendRequest(user.id)
                            "cancel" -> user.requestId?.let { viewModel.cancelFriendRequest(it) }
                            "accept" -> user.requestId?.let { viewModel.responseFriendRequest(it, "accepted") }
                            "reject" -> user.requestId?.let { viewModel.responseFriendRequest(it, "rejected") }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}