package com.martin.features.home.playerscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.core.db.comment.CommentRequestModel
import com.martin.core.db.comment.VideoCommentModel
import com.martin.core.db.home.VideoModel
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import com.martin.core.repository.CommentsRepository
import com.martin.core.repository.HomeRepository
import com.martin.core.repository.LikeRepository
import com.martin.core.repository.SubscriptionRepository
import com.martin.core.utils.extensions.launchSafeWithErrorHandling
import com.martin.features.home.videoutils.BottomSheetType
import com.martin.features.home.videoutils.UserReaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val commentsRepository: CommentsRepository,
    private val likeRepository: LikeRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val prefs: Prefs,
    private val prefUtils: PrefUtils
) : ViewModel() {
    val toastMessage = Channel<String>()
    val video = MutableStateFlow<VideoModel?>(null)
    val comments = MutableStateFlow<List<VideoCommentModel>?>(null)
    val bottomSheetType = MutableStateFlow<BottomSheetType>(BottomSheetType.None)
    val videoReaction = MutableStateFlow(UserReaction.NONE)
    val commentReaction = MutableStateFlow(UserReaction.NONE)
    val currentUser = prefUtils.getUserDetails()
    val videoLiked = MutableStateFlow(false)
    val isLiking = MutableStateFlow(false)
    val isDisliking = MutableStateFlow(false)
    val isSubscribing = MutableStateFlow(false)
    val commentLoading = MutableStateFlow(false)



    fun getVideoById(id: String) {
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                val response = homeRepository.getVideoById(id)
                response.second?.let {
                    video.value = it
                    getAllVideoComments(it.id.toString())
                    videoReaction.value =
                        if (it.liked == true) UserReaction.LIKE else UserReaction.NONE
                }

            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }

    fun getAllVideoComments(id: String) {
        commentLoading.value = true
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                try {
                    val response = commentsRepository.getAllComments(id)
                    response.second?.let {
                        comments.value = it
                    }
                }finally {
                    commentLoading.value = false
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
                commentLoading.value = false
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewComment(videoId: String, commentRequestModel: CommentRequestModel) {
        val tempComment = VideoCommentModel(
            id = System.currentTimeMillis().toString(), // Temporary ID
            content = commentRequestModel.content,
            createdAt = java.time.Instant.now().toString(),
            user = currentUser,
            likeCount = 0,
            isLiked = false
        )
        val currentList = comments.value?.toMutableList() ?: mutableListOf()
        currentList.add(0, tempComment) // Add at top
        comments.value = currentList


        viewModelScope.launchSafeWithErrorHandling(
            block = {
                commentsRepository.addNewComment(videoId,commentRequestModel)
            },
            onError = {
                toastMessage.trySend("Failed to send comment")
            }
        )
    }

    fun toggleLikeOnComment(commentId: String) {
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                commentsRepository.toggleLikeOnComment(commentId)
            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }

    fun toggleLikeOnVideo(videoId: String) {
        if (isLiking.value) return
        isLiking.value = true
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                try {
                    val response = likeRepository.toggleVideoLike(videoId)
                    response.second?.let {
                        videoLiked.value = it.liked == true
                    }
                } finally {
                    isLiking.value = false
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
                isLiking.value = false
            }
        )
    }
    fun toggleDislikeOnVideo(videoId: String) {
        if (isDisliking.value) return
        isDisliking.value = true
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                try {
                    val response = likeRepository.toggleVideoLike(videoId)
                    response.second?.let {
                        videoLiked.value = it.liked == true
                    }
                } finally {
                    isDisliking.value = false
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
                isDisliking.value = false
            }
        )
    }

    fun toggleSubscription(channelId: String) {
        Log.d("checkId", channelId)
        if (isSubscribing.value) return
        isSubscribing.value = true
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                try {
                    subscriptionRepository.toggleSubscription(channelId)
                } finally {
                    isSubscribing.value = false
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
                isSubscribing.value = false
            }
        )
    }



}