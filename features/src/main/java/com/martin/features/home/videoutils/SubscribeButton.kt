package com.martin.features.home.videoutils

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.martin.core.ui.roboto

@Composable
fun CustomSubscribeButton(
    modifier: Modifier = Modifier,
    initialText: String = "Subscribe",
    icon: ImageVector = Icons.Default.Check,
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



@Preview(showBackground = true)
@Composable
fun PreviewSubscribeButton() {
    CustomSubscribeButton()
}

