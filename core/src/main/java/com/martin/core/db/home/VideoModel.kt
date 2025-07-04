package com.martin.core.db.home

import com.martin.core.db.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoModel(
    @Json(name = "_id")
    val id: String?=null,
    @Json(name = "videoFile")
    val videoFile: String?=null,
    @Json(name = "thumbnail")
    val thumbnail: String?=null,
    @Json(name = "title")
    val title: String?=null,
    @Json(name = "description")
    val description: String?=null,
    @Json(name = "duration")
    val duration: Double?=null,
    @Json(name = "views")
    val views: Int?=null,
    @Json(name = "owner")
    val owner: User?=null,
    @Json(name = "likeCount")
    val likeCount: Int?=null,
    @Json(name = "liked")
    val liked: Boolean?=null,
    @Json(name = "createdAt")
    val createdAt: String?=null,
    @Json(name = "updatedAt")
    val updatedAt: String?=null,
    @Json(name ="isSubscribed")
    val isSubscribed: Boolean?=null,
    @Json(name = "subscriberCount")
    val subscriberCount: Int?=null
)
