package com.updavid.liveoci_hilt.core.ui.atoms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 8.dp),
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        modifier = modifier
    )
}