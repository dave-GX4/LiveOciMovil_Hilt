package com.updavid.liveoci_hilt.features.user.presentation.page

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.core.ui.atoms.SectionTitle
import com.updavid.liveoci_hilt.features.user.presentation.components.DangerZoneItem
import com.updavid.liveoci_hilt.features.user.presentation.components.EditableProfileRow
import com.updavid.liveoci_hilt.features.user.presentation.viewmodel.ProfileEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditPage(
    viewModel: ProfileEditViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.isError, uiState.isSuccess) {
        uiState.isError?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearMessages()
        }
        uiState.isSuccess?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessages()
            if (it == "Cuenta eliminada") onLogout()
        }
    }

    if (uiState.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.toggleEdit("delete", false) },
            title = { Text("¿Eliminar cuenta?", fontWeight = FontWeight.Bold) },
            text = { Text("Esta acción borrará todos tus datos de forma permanente. No podrás deshacerlo.") },
            confirmButton = {
                TextButton(onClick = viewModel::onDeleteAccount) {
                    Text("Eliminar", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.toggleEdit("delete", false) }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBackIosNew, null, modifier = Modifier.size(20.dp))
                        }
                    },
                    title = { Text("Información de cuenta", fontWeight = FontWeight.Bold) }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(24.dp)
            ) {
                SectionTitle("Información General")
                EditableProfileRow(
                    label = "Nombre completo",
                    currentValue = uiState.user?.name ?: "Cargando...",
                    newValue = uiState.newName,
                    error = uiState.newNameError,
                    isEditing = uiState.editName,
                    onValueChange = viewModel::onNameChanged,
                    onEditClick = { viewModel.toggleEdit("name", true) },
                    onCancelClick = { viewModel.toggleEdit("name", false) },
                    onSaveClick = viewModel::updateName
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

                SectionTitle("Identidad de la cuenta")
                EditableProfileRow(
                    label = "Correo electrónico",
                    currentValue = uiState.user?.email ?: "Cargando...",
                    newValue = uiState.newEmail,
                    error = uiState.newEmailError,
                    isEditing = uiState.editEmail,
                    onValueChange = viewModel::onEmailChanged,
                    onEditClick = { viewModel.toggleEdit("email", true) },
                    onCancelClick = { viewModel.toggleEdit("email", false) },
                    onSaveClick = viewModel::updateEmail
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

                SectionTitle("Seguridad de la cuenta")
                if (uiState.editPass) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Nueva Contraseña", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)
                        OutlinedTextField(
                            value = uiState.newPass,
                            onValueChange = viewModel::onPasswordChanged,
                            modifier = Modifier.fillMaxWidth(),
                            isError = uiState.newPassError != null,
                            supportingText = { uiState.newPassError?.let { Text(it) } },
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = uiState.confirmationPass,
                            onValueChange = viewModel::onConfirmationChanged,
                            modifier = Modifier.fillMaxWidth(),
                            isError = uiState.confirmationPassError != null,
                            supportingText = { uiState.confirmationPassError?.let { Text(it) } },
                            label = { Text("Confirmar contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { viewModel.toggleEdit("pass", false) }) { Text("Cancelar") }
                            Button(
                                onClick = viewModel::updatePassword,
                                enabled = uiState.newPassError == null && uiState.confirmationPassError == null && uiState.newPass.isNotEmpty()
                            ) { Text("Actualizar") }
                        }
                    }
                } else {
                    EditableProfileRow(
                        label = "Contraseña",
                        currentValue = "********",
                        newValue = "",
                        error = null,
                        isEditing = false,
                        onValueChange = {},
                        onEditClick = { viewModel.toggleEdit("pass", true) },
                        onCancelClick = {},
                        onSaveClick = {},
                        isPassword = true
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text("Zona de Peligro", color = Color.Red, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                DangerZoneItem(onDeleteClick = { viewModel.toggleEdit("delete", true) })
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}