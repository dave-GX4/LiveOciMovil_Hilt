package com.updavid.liveoci_hilt.features.user.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DangerZoneItem(onDeleteClick: () -> Unit) {
    Card(
        onClick = onDeleteClick,
        modifier = Modifier.fillMaxWidth(),
        colors = cardColors(containerColor = Color(0xFFFFEBEB)),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFFFD1D1))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Default.Delete, null, tint = Color.Red)
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text("Eliminar Cuenta", fontWeight = FontWeight.Bold, color = Color.Red)
                Text("Esta acción es permanente", fontSize = 11.sp, color = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}