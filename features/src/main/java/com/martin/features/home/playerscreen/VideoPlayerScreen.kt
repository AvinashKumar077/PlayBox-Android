package com.martin.features.home.playerscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.martin.features.home.videoutils.OneShotActionChip
import com.martin.features.home.videoutils.ToggleActionChip
import com.martin.features.home.videoutils.UserReaction
import com.martin.features.home.videoutils.VideoPlayer


@Composable
fun VideoPlayerScreen(videoUrl: String?) {
    var isSubscribed by remember { mutableStateOf(false) }
    var reaction by remember { mutableStateOf(UserReaction.NONE) }
    val buttonColor by animateColorAsState(
        targetValue = if (isSubscribed) Color.Transparent else Color.White,
        animationSpec = tween(durationMillis = 300)
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSubscribed) Color.Gray else Color.Black,
        animationSpec = tween(durationMillis = 300)
    )

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        VideoPlayer(videoUrl.orEmpty())

        Column(Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Video Player / yeha video ka title ayega",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 12.dp, bottom = 6.dp)
                )
                Text(
                    text = "4.6 crore views • 1 hour ago • ...more",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://thewaltdisneycompany.com/app/uploads/2022/08/1920_AVTR-566.jpg"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "channel name",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))

                // Animated Subscribe Button
                Button(
                    onClick = { isSubscribed = !isSubscribed },
                    shape = CircleShape,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = contentColor
                    )
                ){
                    Text(text = if (isSubscribed) "Subscribed" else "Subscribe")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ToggleActionChip(
                    label = "Like",
                    iconOutlined = Icons.Outlined.ThumbUp,
                    iconFilled = Icons.Filled.ThumbUp,
                    isSelected = reaction == UserReaction.LIKE
                ) {
                    reaction = if (reaction == UserReaction.LIKE) UserReaction.NONE else UserReaction.LIKE
                }
                ToggleActionChip(
                    label = "Dislike",
                    iconOutlined = Icons.Outlined.ThumbDown,
                    iconFilled = Icons.Filled.ThumbDown,
                    isSelected = reaction == UserReaction.DISLIKE
                ) {
                    reaction = if (reaction == UserReaction.DISLIKE) UserReaction.NONE else UserReaction.DISLIKE
                }
                OneShotActionChip("Share", Icons.Outlined.Share) {
                    // trigger share sheet
                }
                OneShotActionChip("Report", Icons.Outlined.Flag) {
                    // show report confirmation
                }
            }
        }
    }
}


