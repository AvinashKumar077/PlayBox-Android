package com.martin.core.db.auth

import com.martin.core.db.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "user")
    val user: User?=null,
    @Json(name = "accessToken")
    val accessToken: String?=null,
    @Json(name = "refreshToken")
    val refreshToken: String?=null
)
