package com.martin.core.repository

import com.martin.core.db.comment.LikeModel
import com.martin.core.db.getResponse
import com.martin.core.network.ApiService
import javax.inject.Inject

class LikeRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun toggleVideoLike(id: String):Pair<String?, LikeModel?> {
       return apiService.toggleVideoLike(id).getResponse()
    }
}