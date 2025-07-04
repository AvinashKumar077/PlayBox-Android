package com.martin.core.repository

import com.martin.core.db.comment.CommentRequestModel
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
    suspend fun toggleLikeOnComment(commentId: String){
        return apiService.toggleLikeOnComment(commentId)
    }
    suspend fun addNewComment(videoId: String,commentRequestModel: CommentRequestModel){
        return apiService.addNewComment(videoId,commentRequestModel)
    }

}