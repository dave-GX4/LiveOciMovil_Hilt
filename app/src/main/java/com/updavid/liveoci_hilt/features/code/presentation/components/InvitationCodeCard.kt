package com.updavid.liveoci_hilt.features.code.presentation.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.QrCode
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import java.time.format.DateTimeFormatter

@Composable
fun InvitationCodeCard(
    codeData: Code,
    isCodeVisible: Boolean,
    onToggleVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayedCode = if (isCodeVisible) codeData.code else "••••••••"
    val formatter = DateTimeFormatter.ofPattern("dd MMM, HH:mm")

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF171A22),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.QrCode,
                        contentDescription = null,
                        tint = Color(0xFFAEBBFF),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Código de vinculación",
                        color = Color(0xFFAEBBFF),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(codeData.code))
                            Toast.makeText(context, "Código copiado", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF252936))
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = "Copiar código",
                            tint = Color(0xFFAEBBFF),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onToggleVisibility,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF252936))
                    ) {
                        Icon(
                            imageVector = if (isCodeVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = "Alternar visibilidad",
                            tint = Color(0xFFAEBBFF),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F1117))
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayedCode,
                    color = Color.White,
                    fontSize = 32.sp,
                    letterSpacing = if (isCodeVisible) 4.sp else 8.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Timer,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Expira", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                    }
                    Text(
                        text = codeData.expiresAt.format(formatter),
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }

                codeData.regeneratedAt?.let { regenDate ->
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Actualizado", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                        }
                        Text(
                            text = regenDate.format(formatter),
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}