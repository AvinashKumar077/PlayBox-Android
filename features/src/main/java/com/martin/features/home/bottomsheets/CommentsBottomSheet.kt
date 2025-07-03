package com.martin.features.home.bottomsheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.martin.core.db.comment.VideoCommentModel
import com.martin.core.ui.roboto
import com.martin.core.ui.sans
import com.martin.core.utils.extensions.noRippleClickable
import com.martin.features.home.playerscreen.component.CommentItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheetContent(
    commentList: List<VideoCommentModel>,
    currentUserAvatar: String?,
    onSendClicked : (String)-> Unit,
    onDislikeClicked: () -> Unit,
    onDismiss: () -> Unit,
    onLikeClicked: () -> Unit,
) {
    var showCommentInput by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xff080808))) {


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HorizontalDivider(thickness = 1.dp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(12.dp))
            // Top bar and comment list
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Comments",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                items(commentList.size) {
                    CommentItem(
                        avatar = commentList[it].user?.avatar,
                        userName = commentList[it].user?.userName,
                        content = commentList[it].content,
                        createdAt = commentList[it].createdAt,
                        onLikeClicked = {onLikeClicked()},
                        onDislikeClicked = {onDislikeClicked()}
                    )
                }
            }

            HorizontalDivider(thickness = 1.dp, color = Color.DarkGray)
        }

        // TextField pinned at the bottom
        Box(
            modifier = Modifier
                .noRippleClickable(
                    onClick = {
                        showCommentInput = true
                    }
                )
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color(0xff080808))
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val width = size.width

                    // Top border
                    drawLine(
                        color = Color.DarkGray,
                        start = Offset(0f, 0f),
                        end = Offset(width, 0f),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(currentUserAvatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(start = 10.dp)
                        .size(25.dp)
                        .clip(CircleShape)
                )
                Box(modifier = Modifier.padding(12.dp).weight(1f).background(color = Color(0xff111111), shape = RoundedCornerShape(2.dp)).height(32.dp),
                    contentAlignment = Alignment.CenterStart){
                    Text("Add a comment...", color = Color.Gray, modifier = Modifier.padding(start = 8.dp),fontFamily = sans, fontWeight = FontWeight.Normal)
                }

            }
        }

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        if (showCommentInput) {
            CommentInputBottomSheet(
                onSend = { commentText ->
                    coroutineScope.launch {
                        sheetState.hide()
                        showCommentInput = false
                    }
                },
                onDismiss = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showCommentInput = false
                    }
                },
                sheetState = sheetState,
                currentUserAvatar = currentUserAvatar,
            )
        }
    }
}



@Preview
@Composable
fun PreviewCommentBottomSheet() {
    CommentBottomSheetContent(
        commentList = listOf(
            VideoCommentModel(
                id = "1",
                content = "this is the comment1",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "2",
                content = "this is the comment2",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment3",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment3",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment3",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment3",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment3",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment5",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment6",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment7",
                createdAt = "2025-03-28T10:00:00.000Z",
            ),
            VideoCommentModel(
                id = "3",
                content = "this is the comment9",
                createdAt = "2025-03-28T10:00:00.000Z",
            )
        ),
        onDismiss = {},
        onLikeClicked = {},
        currentUserAvatar = "",
        onSendClicked = {},
        onDislikeClicked = {}
    )
}