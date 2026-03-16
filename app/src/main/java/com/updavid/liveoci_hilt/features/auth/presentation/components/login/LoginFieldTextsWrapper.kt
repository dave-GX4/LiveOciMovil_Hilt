package com.updavid.liveoci_hilt.features.auth.presentation.components.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.core.ui.atoms.TextFieldComponent

@Composable
fun LoginFieldTextsWrapper(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {

    val isFormValid = email.isNotBlank() && password.isNotBlank()
            && emailError == null && passwordError == null

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextFieldComponent(
            value = email,
            onValueChange = onEmailChange,
            label = "Correo Electrónico",
            errorMessage = emailError,
            keyboardType = KeyboardType.Email
        )

        TextFieldComponent(
            value = password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            errorMessage = passwordError,
            isPassword = true
        )

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { /* Lógica de recuperar contraseña */ }
                .padding(vertical = 4.dp)
        )

        Button(
            onClick = onLoginClick,
            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.Black),
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Finalizar Registro", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}