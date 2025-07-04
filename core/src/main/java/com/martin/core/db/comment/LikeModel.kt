package com.martin.core.db.comment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LikeModel(
    @Json(name = "liked")
    val liked: Boolean?=null,
    @Json(name = "likeCount")
    val likeCount: Int? = null
)
