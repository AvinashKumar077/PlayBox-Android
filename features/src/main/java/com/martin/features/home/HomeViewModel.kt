package com.martin.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.core.db.home.VideoModel
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import com.martin.core.repository.HomeRepository
import com.martin.core.utils.extensions.launchSafeWithErrorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val prefUtils: PrefUtils,
    private val prefs: Prefs,
) : ViewModel() {
    val toastMessage = Channel<String>()
    val videoList = MutableStateFlow<List<VideoModel>?>(null)

    init {
        getAllVideos()
    }

    fun getAllVideos(){
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                val response = homeRepository.getAllVideos()
                response.second?.let {
                    videoList.value = it
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }

}