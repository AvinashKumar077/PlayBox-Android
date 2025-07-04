package com.martin.features.home.playerscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.martin.features.home.videoutils.UserReaction

@Composable
fun CommentItem(
    avatar: String?,
    userName: String?,
    content: String?,
    createdAt: String?,
    onLikeClicked: () -> Unit,
    isCommentLiked: Boolean,
    likeCount: Int?,
) {
    val (commentReaction, setCommentReaction) = remember(isCommentLiked) {
        mutableStateOf(if (isCommentLiked) UserReaction.LIKE else UserReaction.NONE)
    }
    var localLikeCount by remember { mutableIntStateOf(likeCount ?: 0) }

    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
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
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = if(commentReaction == UserReaction.LIKE) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "like",
                tint = if(commentReaction == UserReaction.LIKE) Color.Red else Color.White,
                modifier = Modifier.padding(end = 10.dp).size(16.dp).clickable(
                    onClick = {
                        if (commentReaction == UserReaction.LIKE) {
                            setCommentReaction(UserReaction.NONE)
                            localLikeCount = (localLikeCount - 1).coerceAtLeast(0)
                        } else {
                            setCommentReaction(UserReaction.LIKE)
                            localLikeCount += 1
                            onLikeClicked()
                        }
                    }
                )
            )
            Text(
                text = localLikeCount.toString(),
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 10.dp),
                fontFamily = sans,
                fontWeight = FontWeight.Normal
            )
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
        isCommentLiked = true,
        likeCount = 34,
    )
}