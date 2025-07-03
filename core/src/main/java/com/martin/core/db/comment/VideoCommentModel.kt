package com.martin.core.db.comment

import com.martin.core.db.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoCommentModel(
    @Json(name = "id")
    val id: String?=null,
    @Json(name = "content")
    val content: String?=null,
    @Json(name = "owner")
    val user: User?=null,
    @Json(name = "createdAt")
    val createdAt: String?=null,
    @Json(name = "likeCount")
    val likeCount: Int?=null,
    @Json(name = "isLiked")
    val isLiked: Boolean?=null,
    @Json(name = "replies")
    val replies: List<ReplyModel>?=null,
    @Json(name = "replyCount")
    val replyCount: Int?=null,
)

@JsonClass(generateAdapter = true)
data class ReplyModel(
    @Json(name = "id")
    val id: String?=null,
    @Json(name = "content")
    val content: String?=null,
    @Json(name = "owner")
    val user: User?=null,
    @Json(name = "createdAt")
    val createdAt: String?=null,
    @Json(name = "likeCount")
    val likeCount: Int?=null,
    @Json(name = "isLiked")
    val isLiked: Boolean?=null,
)

