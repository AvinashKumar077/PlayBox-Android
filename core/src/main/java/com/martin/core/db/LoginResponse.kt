package com.martin.core.db

import com.google.gson.annotations.JsonAdapter
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
