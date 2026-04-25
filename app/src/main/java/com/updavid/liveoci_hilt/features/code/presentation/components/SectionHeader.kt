package com.updavid.liveoci_hilt.features.code.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(title: String, description: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(description, fontSize = 14.sp, color = Color.Gray, lineHeight = 18.sp)
    }
}