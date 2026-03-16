package com.updavid.liveoci_hilt.core.ui.atoms

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    appBackgroundColor: Color,
    modifier: Modifier = Modifier,
    onItemSelected: (Int) -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val barBackgroundColor = if (isDarkTheme) Color(0xFF152A1F) else Color.White
    val finalAppBackground = if (isDarkTheme) Color(0xFF0B140F) else appBackgroundColor
    val unselectedIconColor = Color(0xFF94A3B8)

    val navItems = listOf(
        Icons.Rounded.Home,
        Icons.Rounded.Explore,
        Icons.Filled.Add,
        Icons.Rounded.BarChart,
        Icons.Rounded.Person
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            // Dar margen arriba para que el ícono pueda flotar sin cortarse
            .padding(top = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Barra del BootomBar
        Box(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .height(72.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(percent = 50),
                    ambientColor = Color.Black.copy(alpha = 0.15f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .background(barBackgroundColor, RoundedCornerShape(percent = 50))
        )
        // Se colocan encima del fondo. Al no tener "shadow" este Row, los hijos pueden salir libres
        Row(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, icon ->
                AnimatedNavItem(
                    icon = icon,
                    isSelected = selectedIndex == index,
                    activeColor = MaterialTheme.colorScheme.primary,
                    inactiveColor = unselectedIconColor,
                    barBackgroundColor = barBackgroundColor,
                    appBackgroundColor = finalAppBackground,
                    onClick = { onItemSelected(index) }
                )
            }
        }
    }
}

@Composable
fun AnimatedNavItem(
    icon: ImageVector,
    isSelected: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    barBackgroundColor: Color,
    appBackgroundColor: Color,
    onClick: () -> Unit
) {
    val yOffset by animateDpAsState(
        targetValue = if (isSelected) (-24).dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "yOffset"
    )

    val sphereSize by animateDpAsState(
        targetValue = if (isSelected) 60.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "sphereSize"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) activeColor else inactiveColor,
        animationSpec = tween(300),
        label = "iconColor"
    )

    val dotScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(300),
        label = "dotScale"
    )

    Box(
        modifier = Modifier
            .width(56.dp)
            .height(56.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp)
                .size(5.dp)
                .scale(dotScale)
                .background(activeColor, CircleShape)
        )

        Box(
            modifier = Modifier
                .offset(y = yOffset)
                .size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            if (sphereSize > 0.dp) {
                Box(
                    modifier = Modifier
                        .size(sphereSize)
                        .border(4.dp, appBackgroundColor, CircleShape)
                        .shadow(
                            elevation = 12.dp,
                            shape = CircleShape,
                            ambientColor = activeColor.copy(alpha = 0.6f),
                            spotColor = activeColor.copy(alpha = 0.6f)
                        )
                        .background(barBackgroundColor, CircleShape)
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}