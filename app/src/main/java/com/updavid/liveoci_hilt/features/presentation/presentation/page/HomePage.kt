package com.updavid.liveoci_hilt.features.presentation.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.core.ui.atoms.BottomNavigationBar

@Composable
fun HomePage(
    onActivities: () -> Unit,
    onProfile: () -> Unit,
    onHome: () -> Unit,
    onAnalysis: () -> Unit,
    onGenerateActivity: () -> Unit
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val screenBackgroundColor = Color(0xFFF8FAFC)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(screenBackgroundColor)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Pantalla Activa: $selectedItemIndex",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                // AQUI IRÍAN TUS COMPONENTES: LazyColumn, etc.
            }
            BottomNavigationBar(
                selectedIndex = selectedItemIndex,
                appBackgroundColor = screenBackgroundColor,
                modifier = Modifier.align(Alignment.BottomCenter),
                onItemSelected = { newIndex ->
                    selectedItemIndex = newIndex
                    when (newIndex) {
                        0 -> onHome()
                        1 -> onActivities()
                        2 -> onGenerateActivity()
                        3 -> onAnalysis()
                        4 -> onProfile()
                    }
                }
            )
        }
    }
}