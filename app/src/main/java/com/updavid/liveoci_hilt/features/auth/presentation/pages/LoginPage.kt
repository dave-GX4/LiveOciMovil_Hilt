package com.updavid.liveoci_hilt.features.auth.presentation.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.features.auth.presentation.components.FooterSection
import com.updavid.liveoci_hilt.features.auth.presentation.components.login.HeaderLoginSection
import com.updavid.liveoci_hilt.features.auth.presentation.components.login.LoginFieldTextsWrapper
import com.updavid.liveoci_hilt.features.auth.presentation.viewmodels.LoginViewModel

@Composable
fun LoginPage(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.authError) {
        uiState.authError?.let { errorMensaje ->
            Toast.makeText(context, errorMensaje, Toast.LENGTH_LONG).show()
            viewModel.clearAuthError()
        }
    }

    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                HeaderLoginSection()

                Spacer(modifier = Modifier.height(20.dp))

                LoginFieldTextsWrapper(
                    email = uiState.loginEmail,
                    password = uiState.loginPassword,
                    emailError = uiState.loginEmailError,
                    passwordError = uiState.loginPasswordError,
                    onEmailChange = viewModel::onEmailChanged,
                    onPasswordChange = viewModel::onPasswordChanged,
                    onLoginClick = viewModel::onAuthentication,
                )

                Spacer(modifier = Modifier.weight(1f))

                FooterSection(
                    textLabel = "¿No tienes una cuenta?",
                    actionText = "Regístrate",
                    onActionClick = onNavigateToRegister
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}