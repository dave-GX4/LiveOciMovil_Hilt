package com.updavid.liveoci_hilt.features.user.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditableProfileRow(
    label: String,
    currentValue: String,
    newValue: String,
    error: String?,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Gray)

            Text(
                text = if (isEditing) "Cancelar" else "Editar",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.clickable { if (isEditing) onCancelClick() else onEditClick() }
            )
        }

        AnimatedContent(targetState = isEditing, label = "edit_animation") { editing ->
            if (editing) {
                Column {
                    OutlinedTextField(
                        value = newValue,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        isError = error != null,
                        supportingText = { error?.let { Text(it, color = Color.Red) } },
                        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier.align(Alignment.End).padding(top = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Guardar")
                    }
                }
            } else {
                Text(
                    text = if (isPassword) "********" else currentValue,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}