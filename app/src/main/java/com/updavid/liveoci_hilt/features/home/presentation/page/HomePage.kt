package com.updavid.liveoci_hilt.features.home.presentation.page

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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.updavid.liveoci_hilt.core.ui.atoms.BottomNavigationBar
import com.updavid.liveoci_hilt.features.bored.presentation.components.ActivityItemCard
import com.updavid.liveoci_hilt.features.home.presentation.components.RecommendedCard
import com.updavid.liveoci_hilt.features.home.presentation.components.UserHeader
import com.updavid.liveoci_hilt.features.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomePage(
    viewModel: HomeViewModel,
    onActivities: () -> Unit,
    onProfile: () -> Unit,
    onHome: () -> Unit,
    onAnalysis: () -> Unit,
    onBored: () -> Unit
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                UserHeader(
                    greeting = uiState.greeting,
                    greetingIcon = uiState.greetingIcon,
                    userName = uiState.userName,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recomendado para ti",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Actualizar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { viewModel.fetchRecommendedActivity() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    uiState.isLoading -> {
                        Box(modifier = Modifier.fillMaxWidth().height(160.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    uiState.isError != null -> {
                        Text(text = "Error al cargar la recomendación.", color = Color.Red)
                    }
                    uiState.recommendedActivity != null -> {
                        RecommendedCard(activity = uiState.recommendedActivity!!)
                    }
                }
            }

            BottomNavigationBar(
                selectedIndex = selectedItemIndex,
                appBackgroundColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.align(Alignment.BottomCenter),
                onItemSelected = { newIndex ->
                    if (newIndex != 2) selectedItemIndex = newIndex
                    when (newIndex) {
                        0 -> onHome()
                        1 -> onBored()
                        2 -> onActivities()
                        3 -> onAnalysis()
                        4 -> onProfile()
                    }
                }
            )
        }
    }
}