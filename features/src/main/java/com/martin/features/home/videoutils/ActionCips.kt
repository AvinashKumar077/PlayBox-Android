package com.martin.features.home.videoutils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BaseActionChip(
    label: String,
    icon: ImageVector,
    isSelected: Boolean = false,
    toggleable: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (isSelected && toggleable) Color.White.copy(alpha = 0.2f)
        else Color.Transparent,
        animationSpec = tween(150)
    )

    val elevation by animateDpAsState(
        if (isSelected && toggleable) 6.dp else 2.dp,
        animationSpec = tween(150)
    )

    val scale by animateFloatAsState(
        if (isSelected && toggleable) 1.05f else 1f,
        animationSpec = tween(150)
    )

    Surface(
        modifier = Modifier
            .scale(scale)
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(50),
        color = backgroundColor,
        tonalElevation = elevation
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected && toggleable) Color.White else Color.White
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = if (isSelected && toggleable) Color.White else Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun ToggleActionChip(
    label: String,
    iconOutlined: ImageVector,
    iconFilled: ImageVector,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    BaseActionChip(
        label = label,
        icon = if (isSelected) iconFilled else iconOutlined,
        isSelected = isSelected,
        toggleable = true,
        onClick = onToggle
    )
}


@Composable
fun OneShotActionChip(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    BaseActionChip(
        label = label,
        icon = icon,
        isSelected = false,
        toggleable = false,
        onClick = onClick
    )
}