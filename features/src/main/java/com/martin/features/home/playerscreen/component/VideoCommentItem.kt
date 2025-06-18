package com.martin.features.home.playerscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.martin.core.helper.DateUtils.getTimeAgo
import com.martin.core.ui.sans

@Composable
fun CommentItem(
    avatar: String?,
    userName: String?,
    content: String?,
    createdAt: String?,
    onLikeClicked:()->Unit,
    onDislikeClicked:()->Unit
) {
    Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter(avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 8.dp)
                .size(25.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = "@${userName} • ${getTimeAgo(createdAt.toString())}",
                color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = sans,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = content.toString(),
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = sans,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp,
                    contentDescription = "like",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 20.dp).size(16.dp).clickable(
                        onClick = {
                            onLikeClicked()
                        }
                    )
                )
                Icon(
                    imageVector = Icons.Outlined.ThumbDown,
                    contentDescription = "dislike",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp).clickable(
                        onClick = {
                            onDislikeClicked()
                        }
                    )
                )

            }
        }
    }
}

@Preview
@Composable
fun CommentItemPreview(){
    CommentItem(
        avatar = "https://th.bing.com/th/id/OIP.eTUT6_ZG9aGQ8cACsc7nLQHaFj?w=260&h=195&c=7&r=0&o=5&dpr=1.3&pid=1.7",
        userName = "Martin",
        content = "this is the content of the comment",
        createdAt = "2025-02-28T10:00:00.000Z",
        onLikeClicked = {},
        onDislikeClicked = {}
    )
}