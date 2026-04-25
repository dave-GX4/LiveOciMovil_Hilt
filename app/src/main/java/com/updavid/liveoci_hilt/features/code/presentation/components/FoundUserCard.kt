package com.updavid.liveoci_hilt.features.code.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser

@Composable
fun FoundUserCard(
    user: FoundUser,
    onAction: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFAEBBFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(user.name.take(1), color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = user.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "#${user.code}", color = Color.Gray, fontSize = 12.sp)
            }

            when {
                user.requestStatus == "accepted" -> {
                    Text("Amigos", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                }

                user.requestStatus == "pending" -> {
                    if (user.isRequester == true) {
                        Button(
                            onClick = { onAction("cancel") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Cancelar", fontSize = 12.sp)
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.End) {
                            Button(
                                onClick = { onAction("accept") },
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("Aceptar", fontSize = 10.sp)
                            }
                            TextButton(onClick = { onAction("reject") }) {
                                Text("Rechazar", color = Color.Gray, fontSize = 10.sp)
                            }
                        }
                    }
                }

                user.requestStatus == "rejected" && user.isRequester == true -> {
                    Text("Espera...", color = Color.Gray, fontStyle = FontStyle.Italic)
                }

                user.requestStatus == null || user.requestStatus == "" -> {
                    Button(
                        onClick = { onAction("send") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAEBBFF))
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(" Enviar", color = Color.Black)
                    }
                }

                else -> {
                    Button(
                        onClick = { onAction("send") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAEBBFF))
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(" Enviar", color = Color.Black)
                    }
                }
            }
        }
    }
}