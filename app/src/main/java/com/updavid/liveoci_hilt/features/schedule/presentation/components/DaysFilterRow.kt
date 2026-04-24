package com.updavid.liveoci_hilt.features.schedule.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaysFilterRow(selectedDay: Int?, onDaySelected: (Int?) -> Unit) {
    val days = listOf(
        null to "Todos", 1 to "Lun", 2 to "Mar", 3 to "Mié",
        4 to "Jue", 5 to "Vie", 6 to "Sáb", 7 to "Dom"
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(days) { (dayValue, dayName) ->
            FilterChip(
                selected = selectedDay == dayValue,
                onClick = { onDaySelected(dayValue) },
                label = { Text(dayName) }
            )
        }
    }
}