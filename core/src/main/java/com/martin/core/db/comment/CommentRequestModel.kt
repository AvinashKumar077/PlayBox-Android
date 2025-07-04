package com.martin.core.db.comment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentRequestModel(
    @Json(name = "content")
    val content:String?=null,
    @Json(name = "parentComment")
    val parentCommentId:String?=null,
)