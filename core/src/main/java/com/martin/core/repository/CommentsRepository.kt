package com.martin.core.repository

import com.martin.core.db.comment.VideoCommentModel
import com.martin.core.db.getResponse
import com.martin.core.network.ApiService
import jakarta.inject.Inject

class CommentsRepository @Inject constructor(
    val apiService: ApiService
) {
    suspend fun getAllComments(id: String): Pair<String?, List<VideoCommentModel>?>{
        return apiService.getAllComments(id = id).getResponse()
    }

}