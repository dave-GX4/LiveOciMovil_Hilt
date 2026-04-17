package com.updavid.liveoci_hilt.features.user.presentation.page

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.core.ui.atoms.SectionTitle
import com.updavid.liveoci_hilt.features.user.presentation.components.CustomChip
import com.updavid.liveoci_hilt.features.user.presentation.viewmodel.TastesViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TastesPage(
    viewModel: TastesViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var newTopicText by remember { mutableStateOf("") }
    var isAddingInterest by remember { mutableStateOf(false) }
    var customInterestText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BackHandler(enabled = state.isEditing) {
        viewModel.cancelEditMode()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil de Gustos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { if (state.isEditing) viewModel.cancelEditMode() else onNavigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    if (!state.isEditing) {
                        IconButton(onClick = { viewModel.enableEditMode() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                        }
                    } else {
                        TextButton(onClick = { viewModel.cancelEditMode() }) {
                            Text("Cancelar", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                )
            )
        },
        bottomBar = {
            if (state.isEditing) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Button(
                        onClick = { viewModel.saveTastes() },
                        enabled = state.isChanged && !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                        } else {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Guardar Preferencias", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Column {
                SectionTitle(
                    text = "Sobre Mí",
                    icon = Icons.Default.Person,
                    fontSize = 20.sp,
                    iconSize = 28.dp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if (state.isEditing) {
                    OutlinedTextField(
                        value = state.description,
                        onValueChange = { viewModel.onDescriptionChanged(it) },
                        placeholder = { Text("Escribe datos precisos sobre tus gustos...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        ),
                        minLines = 3
                    )
                } else {
                    Text(
                        text = state.description.ifEmpty { "Aún no has agregado una descripción." },
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    )
                }
            }

            Column {
                SectionTitle(
                    text = "Mis Intereses",
                    icon = Icons.Default.Favorite,
                    fontSize = 20.sp,
                    iconSize = 28.dp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (state.isEditing) {
                        state.availableInterests.forEach { interest ->
                            val isSelected = state.interests.contains(interest)
                            CustomChip(
                                text = interest,
                                isSelected = isSelected,
                                onClick = { viewModel.onToggleInterest(interest) }
                            )
                        }

                        AnimatedVisibility(
                            visible = !isAddingInterest,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.Transparent,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                modifier = Modifier.height(38.dp).clickable {
                                    isAddingInterest = true
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(Modifier.width(4.dp))
                                    Text("Más", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        AnimatedVisibility(
                            visible = isAddingInterest,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            LaunchedEffect(Unit) { focusRequester.requestFocus() }

                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                modifier = Modifier.height(38.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 16.dp, end = 6.dp)
                                ) {
                                    BasicTextField(
                                        value = customInterestText,
                                        onValueChange = { customInterestText = it },
                                        singleLine = true,
                                        textStyle = LocalTextStyle.current.copy(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        ),
                                        modifier = Modifier
                                            .widthIn(min = 60.dp, max = 120.dp)
                                            .focusRequester(focusRequester),
                                        decorationBox = { innerTextField ->
                                            if (customInterestText.isEmpty()) {
                                                Text("Escribe...", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                                            }
                                            innerTextField()
                                        }
                                    )

                                    IconButton(
                                        onClick = {
                                            if (customInterestText.isNotBlank()) {
                                                viewModel.onAddCustomInterest(customInterestText)
                                            }
                                            customInterestText = ""
                                            isAddingInterest = false
                                            focusManager.clearFocus()
                                        },
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = "Confirmar", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }

                    } else {
                        if (state.interests.isEmpty()) {
                            Text("Sin intereses seleccionados.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            state.interests.forEach { interest ->
                                CustomChip(text = interest, isSelected = true, onClick = {})
                            }
                        }
                    }
                }
            }

            Column {
                SectionTitle(
                    text = "Temas de Estudio",
                    icon = Icons.Default.School,
                    fontSize = 20.sp,
                    iconSize = 28.dp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (state.topics.isEmpty() && !state.isEditing) {
                            Text("No hay temas registrados.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        state.topics.forEachIndexed { index, topic ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(text = topic, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                                }
                                if (state.isEditing) {
                                    IconButton(
                                        onClick = { viewModel.onRemoveTopic(topic) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }

                        if (state.isEditing) {
                            Spacer(modifier = Modifier.height(12.dp))
                            TextField(
                                value = newTopicText,
                                onValueChange = { newTopicText = it },
                                placeholder = { Text("¿Qué estás aprendiendo hoy?", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ),
                                trailingIcon = {
                                    IconButton(
                                        onClick = { viewModel.onAddTopic(newTopicText); newTopicText = "" },
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                                            .size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Agregar", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp))
                                    }
                                },
                                singleLine = true
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}