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
import com.updavid.liveoci_hilt.features.auth.presentation.components.register.HeaderRegisterSection
import com.updavid.liveoci_hilt.features.auth.presentation.components.register.RegisterFieldTextsWrapper
import com.updavid.liveoci_hilt.features.auth.presentation.viewmodels.RegisterViewModel

@Composable
fun RegisterPage(
    viewModel: RegisterViewModel,
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.authError) {
        uiState.authError?.let { errorMensaje ->
            Toast.makeText(context, errorMensaje, Toast.LENGTH_LONG).show()
            viewModel.clearAuthError()
        }
    }

    LaunchedEffect(uiState.isRegisterSuccessful){
        if(uiState.isRegisterSuccessful){
            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
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
                HeaderRegisterSection()

                RegisterFieldTextsWrapper(
                    username = uiState.registerName,
                    email = uiState.registerEmail,
                    password = uiState.registerPassword,
                    passwordConfirmation = uiState.registerConfirmation,
                    isTermsAccepted = uiState.isTermsAcceptedUser,

                    usernameError = uiState.registerNameError,
                    emailError = uiState.registerEmailError,
                    passwordError = uiState.registerPasswordError,
                    passwordConfirmationError = uiState.registerConfirmationError,

                    onNameChange = viewModel::onNameChanged,
                    onEmailChange = viewModel::onEmailChanged,
                    onPasswordChange = viewModel::onPasswordChanged,
                    onPasswordConfirmationChange = viewModel::onConfirmationChanged,
                    onTermsAcceptedChange = viewModel::onTermsAcceptedUserChanged,

                    onRegisterClick = viewModel::onAuthentication
                )

                Spacer(modifier = Modifier.weight(1f))

                FooterSection(
                    textLabel = "¿Ya tienes una cuenta?",
                    actionText = "Inicia sesión",
                    onActionClick = onBack
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}