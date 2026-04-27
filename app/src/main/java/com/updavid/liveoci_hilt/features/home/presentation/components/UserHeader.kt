package com.updavid.liveoci_hilt.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotification

@Composable
fun UserHeader(
    greeting: String,
    greetingIcon: ImageVector,
    userName: String?,
    userPhotoUrl: String?,
    notifications: List<HomeNotification>,
    unreadNotificationCount: Int,
    onNotificationClick: (HomeNotification) -> Unit,
    onMarkAllNotificationsRead: () -> Unit
) {
    var showNotifications by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.size(56.dp)) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userPhotoUrl)
                        .addHeader("Cache-Control", "no-cache")
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .diskCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    val state = painter.state

                    if (
                        state is AsyncImagePainter.State.Loading ||
                        state is AsyncImagePainter.State.Error ||
                        userPhotoUrl == null
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }

                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-2).dp, y = (-2).dp)
                        .background(Color(0xFF4CAF50), CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = greeting,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )

                    Icon(
                        imageVector = greetingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (greeting.contains("noches")) {
                            Color(0xFF9FA8DA)
                        } else {
                            Color(0xFFFFB74D)
                        }
                    )
                }

                Text(
                    text = userName ?: "Invitado",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        IconButton(
            onClick = {
                showNotifications = true
            },
            modifier = Modifier
                .padding(start = 8.dp)
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        ) {
            BadgedBox(
                badge = {
                    if (unreadNotificationCount > 0) {
                        Badge {
                            Text(text = unreadNotificationCount.toString())
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

    if (showNotifications) {
        NotificationBottomSheet(
            notifications = notifications,
            unreadCount = unreadNotificationCount,
            onDismiss = {
                showNotifications = false
            },
            onNotificationClick = { notification ->
                onNotificationClick(notification)
                showNotifications = false
            },
            onMarkAllNotificationsRead = {
                onMarkAllNotificationsRead()
                showNotifications = false
            }
        )
    }
}