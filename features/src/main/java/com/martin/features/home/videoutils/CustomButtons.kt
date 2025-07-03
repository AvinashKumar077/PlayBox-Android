package com.martin.features.home.videoutils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.martin.core.ui.roboto

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

@Composable
fun CustomSubscribeButton(
    modifier: Modifier = Modifier,
    initialText: String = "Subscribe",
    icon: ImageVector = Icons.Default.NotificationsActive,
    onClickAction: (() -> Unit)? = null
) {
    var isSubscribed by remember { mutableStateOf(false) }

    val width by animateDpAsState(targetValue = if (isSubscribed) 32.dp else 100.dp, label = "width")
    val cornerRadius by animateDpAsState(targetValue = if (isSubscribed) 16.dp else 12.dp, label = "corner")

    Box(
        modifier = modifier
            .width(width)
            .height(32.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(if (isSubscribed) Color(0xff111111)else Color.White)
            .clickable {
                isSubscribed = !isSubscribed
                onClickAction?.invoke()
            }
            .animateContentSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isSubscribed) {
            Icon(
                imageVector = icon,
                contentDescription = "Subscribed",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        } else {
            Text(
                text = initialText,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = roboto,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun RotatingSettingsButton(
    isRotated: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "SettingsRotation"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = Color.White,
            modifier = Modifier.rotate(rotationAngle)
        )
    }
}
@Composable
fun PlayPauseButton(
    isPlaying: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = "Play/Pause",
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSubscribeButton() {
    CustomSubscribeButton()
}