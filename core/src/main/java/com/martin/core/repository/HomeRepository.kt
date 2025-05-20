package com.martin.core.repository

import com.martin.core.db.getResponse
import com.martin.core.db.home.VideoModel
import com.martin.core.network.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllVideos(): Pair<String?, List<VideoModel>?>{
        return apiService.getAllVideos().getResponse()
    }
}