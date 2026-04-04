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
import com.updavid.liveoci_hilt.features.user.presentation.components.HeaderProfileSection
import com.updavid.liveoci_hilt.features.user.presentation.components.ProfileOptionItem
import com.updavid.liveoci_hilt.features.user.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    viewModel: ProfileViewModel,
    onNavigateToEditInterests: () -> Unit,
    onLogoutSuccess: () -> Unit ,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess == "Sesión cerrada") {
            onLogoutSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBackIosNew, null, modifier = Modifier.size(20.dp)) }
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
                    email = uiState.user?.email ?: "usuario@ejemplo.com"
                )

                Text(
                    text = "Profile",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                ProfileOptionItem(
                    icon = Icons.Default.SettingsAccessibility,
                    title = "Administrar Usuario",
                    onClick = onNavigateToEditInterests
                )

                Text(
                    text = "Settings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )

                ProfileOptionItem(
                    icon = Icons.Default.NotificationsNone,
                    title = "Notificaciones",
                    onClick = { /* Navegar */ },
                    iconColor = Color(0xFFF1EFFF),
                    iconTint = Color(0xFF6C63FF)
                )

                ProfileOptionItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    onClick = { /* Cambiar */ },
                    iconColor = Color(0xFFE3F2FD),
                    iconTint = Color(0xFF2196F3)
                )

                ProfileOptionItem(
                    icon = Icons.Default.FavoriteBorder,
                    title = "Mis Gustos",
                    onClick = { /* Navegar */ },
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

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = viewModel::onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
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

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}