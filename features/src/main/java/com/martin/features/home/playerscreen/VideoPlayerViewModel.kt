package com.martin.features.home.playerscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.core.db.comment.VideoCommentModel
import com.martin.core.db.home.VideoModel
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import com.martin.core.repository.CommentsRepository
import com.martin.core.repository.HomeRepository
import com.martin.core.utils.extensions.launchSafeWithErrorHandling
import com.martin.features.home.videoutils.BottomSheetType
import com.martin.features.home.videoutils.UserReaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val commentsRepository: CommentsRepository,
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
    val videoList = MutableStateFlow<List<VideoModel>?>(null)

    fun getVideoById(id: String){
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                val response = homeRepository.getVideoById(id)
                response.second?.let {
                    video.value = it
                    getAllVideoComments(it.id.toString())
                }

            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }

    fun getAllVideoComments(id: String){
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                val response = commentsRepository.getAllComments(id)
                response.second?.let {
                    comments.value = it
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }

    fun addNewComment(comment: String){

    }
    fun toggleLikeOnComment(){

    }
    fun toggleLikeOnVideo(){

    }

}