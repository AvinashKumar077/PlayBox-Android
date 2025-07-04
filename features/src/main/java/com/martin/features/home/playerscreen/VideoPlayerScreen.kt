package com.martin.features.home.playerscreen


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.martin.core.R
import com.martin.core.db.comment.CommentRequestModel
import com.martin.core.db.home.VideoModel
import com.martin.core.helper.DateUtils.getTimeAgo
import com.martin.core.ui.sans
import com.martin.core.utils.extensions.noRippleClickable
import com.martin.features.home.components.CommentSectionContent
import com.martin.features.home.components.DescriptionBottomSheetContent
import com.martin.features.home.videoutils.BottomSheetType
import com.martin.features.home.videoutils.CustomSubscribeButton
import com.martin.features.home.videoutils.OneShotActionChip
import com.martin.features.home.videoutils.ToggleActionChip
import com.martin.features.home.videoutils.ToggleActionDislikeChip
import com.martin.features.home.videoutils.UserReaction
import com.martin.features.home.videoutils.VideoPlayer
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(video: VideoModel) {
    val videoPlayerViewModel: VideoPlayerViewModel = hiltViewModel()
    val videoData by videoPlayerViewModel.video.collectAsStateWithLifecycle()
    val commentList by videoPlayerViewModel.comments.collectAsStateWithLifecycle()
    val isLiking by videoPlayerViewModel.isLiking.collectAsStateWithLifecycle()
    val isDisliking by videoPlayerViewModel.isDisliking.collectAsStateWithLifecycle()
    val videoLiked by videoPlayerViewModel.videoLiked.collectAsStateWithLifecycle()
    val isSubscribing by videoPlayerViewModel.isSubscribing.collectAsStateWithLifecycle()
    val commentLoading by videoPlayerViewModel.commentLoading.collectAsStateWithLifecycle()
    val currentUser = videoPlayerViewModel.currentUser
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    LaunchedEffect(Unit) {
        videoPlayerViewModel.getVideoById(video.id.toString())
    }

    val currentSheet by videoPlayerViewModel.bottomSheetType.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        snapshotFlow { currentSheet }
            .collect { type ->
                if (type != BottomSheetType.None) scaffoldState.bottomSheetState.expand()
                else scaffoldState.bottomSheetState.hide()
            }
    }
    val videoReaction by videoPlayerViewModel.videoReaction.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (videoData?.videoFile != null) {
            VideoPlayer(videoData?.videoFile.toString())
        } else {
            Box(modifier = Modifier.aspectRatio(16f / 9f)) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                when (currentSheet) {
                    BottomSheetType.Description -> {
                        videoData?.let {
                            DescriptionBottomSheetContent(
                                likes = it.likeCount,
                                views = it.views,
                                createdDateTime = it.createdAt,
                                description = it.description,
                                channelName = it.owner?.userName,
                                avatar = it.owner?.avatar,
                                title = it.title,
                                onSubscribeClicked = {
                                    coroutineScope.launch {
                                        videoPlayerViewModel.toggleSubscription(it.owner?.userId.toString())
                                    }
                                },
                                onDismiss = {
                                    coroutineScope.launch {
                                        videoPlayerViewModel.bottomSheetType.value =
                                            BottomSheetType.None
                                        scaffoldState.bottomSheetState.hide()
                                    }
                                },
                                isSubscribed = it.isSubscribed==true,
                                subscriberCount = it.subscriberCount?:0,
                                isLoadings = isSubscribing
                            )

                        }
                    }

                    BottomSheetType.None -> {

                    }
                }

            },
            sheetContainerColor = Color(0xff080808),
            containerColor = Color.Black
        ) {
            Column(Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable(
                            onClick = {
                                videoPlayerViewModel.bottomSheetType.value =
                                    BottomSheetType.Description
                            }
                        ))
                {
                    Text(
                        text = video.title.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 6.dp)
                    )
                    Text(
                        text = "${video.views} views • ${getTimeAgo(video.createdAt.toString())} • ...more",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(videoData?.owner?.avatar),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = video.owner?.userName.toString(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontFamily = sans,
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    // Animated Subscribe Button
                    videoData?.takeIf {
                        it.owner?.userId != currentUser?.userId
                    }?.let { data ->
                        CustomSubscribeButton(
                            modifier = Modifier.padding(end = 10.dp),
                            isSubscribed = data.isSubscribed == true,
                            isLoadings = isSubscribing,
                            onClickAction = {
                                coroutineScope.launch {
                                    videoPlayerViewModel.toggleSubscription(data.owner?.userId.toString())
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ToggleActionChip(
                        label = "Like",
                        iconOutlined = Icons.Outlined.ThumbUp,
                        iconFilled = Icons.Filled.ThumbUp,
                        isSelected = videoReaction == UserReaction.LIKE,
                        isLiking = isLiking
                    ) {
                        videoPlayerViewModel.videoReaction.value =
                            if (videoReaction == UserReaction.LIKE) UserReaction.NONE else UserReaction.LIKE
                        coroutineScope.launch {
                            videoPlayerViewModel.toggleLikeOnVideo(videoData?.id.toString())
                        }
                    }
                    ToggleActionDislikeChip(
                        label = "Dislike",
                        iconOutlined = Icons.Outlined.ThumbDown,
                        iconFilled = Icons.Filled.ThumbDown,
                        isSelected = videoReaction == UserReaction.DISLIKE,
                        isDisliking = isDisliking
                    ) {
                        videoPlayerViewModel.videoReaction.value =
                            if (videoReaction == UserReaction.DISLIKE) UserReaction.NONE else UserReaction.DISLIKE
                        coroutineScope.launch {
                            if (videoData?.liked == true || videoLiked) {
                                videoPlayerViewModel.toggleDislikeOnVideo(videoData?.id.toString())
                            }
                        }
                    }
                    OneShotActionChip("Share", Icons.Outlined.Share) {
                        // trigger share sheet
                    }
                    OneShotActionChip("Report", Icons.Outlined.Flag) {
                        // show report confirmation
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                //comment section
                if(commentLoading){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        val animation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.commentloading))
                        LottieAnimation(
                            composition = animation,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }else{
                    commentList?.let {
                        CommentSectionContent(
                            commentList = commentList ?: emptyList(),
                            onLikeClicked = { commentId->
                                coroutineScope.launch {
                                    videoPlayerViewModel.toggleLikeOnComment(commentId)
                                }
                            },
                            currentUserAvatar = currentUser?.avatar,
                            onSendClicked = { comment ->
                                coroutineScope.launch {
                                    videoPlayerViewModel.addNewComment(
                                        videoData?.id.toString(),
                                        CommentRequestModel(
                                            content = comment
                                        )
                                    )
                                }
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

            }
        }
    }
}
