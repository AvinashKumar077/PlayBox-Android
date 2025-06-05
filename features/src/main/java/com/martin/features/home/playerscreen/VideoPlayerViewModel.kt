package com.martin.features.home.playerscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.core.db.home.VideoModel
import com.martin.core.repository.HomeRepository
import com.martin.core.utils.extensions.launchSafeWithErrorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    val toastMessage = Channel<String>()
    val video = MutableStateFlow<VideoModel?>(null)
    val showDescriptionBottomSheet = MutableStateFlow(false)

    fun getVideoById(id: String){
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                val response = homeRepository.getVideoById(id)
                response.second?.let {
                    video.value = it
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }
}