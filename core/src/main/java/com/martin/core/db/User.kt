package com.martin.core.db

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "_id")
    val userId: Long? = null,
    @Json(name = "userName")
    val userName: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "fullName")
    val fullName: String? = null,
    @Json(name = "avatar")
    val avatar: String? = null,
    @Json(name = "coverImage")
    val coverImage: String? = null
)

