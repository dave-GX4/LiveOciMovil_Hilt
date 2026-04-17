package com.updavid.liveoci_hilt.features.user.presentation.page

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SettingsAccessibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.core.ui.atoms.SectionTitle
import com.updavid.liveoci_hilt.features.user.presentation.components.HeaderProfileSection
import com.updavid.liveoci_hilt.features.user.presentation.components.ProfileOptionItem
import com.updavid.liveoci_hilt.features.user.presentation.components.ProfileSwitchItem
import com.updavid.liveoci_hilt.features.user.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    viewModel: ProfileViewModel,
    onNavigateTastes: () -> Unit,
    onNavigateToEditInterests: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess == "Sesión cerrada") {
            onLogoutSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tu Perfil", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, null, modifier = Modifier.size(20.dp))
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, null) }
                },
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefresh,
            onRefresh = { viewModel.refreshUserRemote() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                HeaderProfileSection(
                    name = uiState.user?.name ?: "Usuario",
                    email = uiState.user?.email ?: "usuario@ejemplo.com",
                    ocio = uiState.user?.leisureType?: "Sin especificar"
                )

                SectionTitle(
                    text = "Más Opciones",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )
                ProfileOptionItem(
                    icon = Icons.Default.FavoriteBorder,
                    title = "Mis Gustos",
                    onClick = onNavigateTastes,
                    iconColor = Color(0xFFFFF0F3),
                    iconTint = Color(0xFFFF4D6D)
                )

                ProfileOptionItem(
                    icon = Icons.Default.Schedule,
                    title = "Mis Horarios",
                    onClick = { /* Navegar */ },
                    iconColor = Color(0xFFE8F5E9),
                    iconTint = Color(0xFF4CAF50)
                )

                SectionTitle(
                    text = "Seguridad y Privacidad",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )

                ProfileOptionItem(
                    icon = Icons.Default.SettingsAccessibility,
                    title = "Administrar Usuario",
                    onClick = onNavigateToEditInterests
                )

                SectionTitle(
                    text = "Configuraciones de app",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )

                ProfileSwitchItem(
                    icon = Icons.Default.NotificationsNone,
                    title = "Notificaciones",
                    subtitle = "Recibe avisos sobre tus horarios",
                    checked = uiState.notificationStatus,
                    onCheckedChange = viewModel::onNotificationToggled,
                    iconColor = Color(0xFFF1EFFF),
                    iconTint = Color(0xFF6C63FF)
                )

                ProfileSwitchItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    subtitle = "Cambia el aspecto de la aplicación",
                    checked = uiState.darkModeStatus,
                    onCheckedChange = viewModel::onDarkModeToggled,
                    iconColor = Color(0xFFE3F2FD),
                    iconTint = Color(0xFF2196F3)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = viewModel::onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F9)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = null
                ) {
                    Text(
                        text = "Sign Out",
                        color = Color(0xFF5C5EAB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(130.dp))
            }
        }
    }
}