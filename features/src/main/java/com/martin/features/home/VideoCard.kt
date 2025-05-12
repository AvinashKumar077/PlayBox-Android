package com.martin.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.martin.core.db.User
import com.martin.core.db.home.VideoModel
import com.martin.core.helper.getTimeAgo
import com.martin.core.utils.extensions.noRippleClickable

@Composable
fun VideoCard(video: VideoModel, onClick: (VideoModel) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black)
        .noRippleClickable(
            onClick = {
                onClick(video)
            }
        )
    ) {
        Image(
            painter = rememberAsyncImagePainter(video.thumbnail),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(16f/9f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(video.owner?.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = video.title.toString(), color = Color.White, maxLines = 2)
                Text(
                    text = "${video.owner?.userId} • ${video.views} views • ${getTimeAgo(video.createdAt.toString())}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f)) 
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun VideoCardPreview() {
    val dummyVideo = VideoModel(
        videoFile = "https://example.com/video.mp4",
        thumbnail = "https://i.ytimg.com/vi/5xX5MmmThiU/maxresdefault.jpg",
        title = "Exploring Compose: Full Tutorial",
        description = "Learn Jetpack Compose from scratch in this in-depth tutorial!",
        duration = 12.5,
        views = 12345,
        createdAt = "2025-05-01T10:15:30.000Z",
        owner = User(
            userId = "martin_dev",
            avatar = "https://png.pngtree.com/png-clipart/20230927/original/pngtree-man-avatar-image-for-profile-png-image_13001877.png"
        )
    )

    VideoCard(
        video = dummyVideo,
        onClick = {}
    )
}
