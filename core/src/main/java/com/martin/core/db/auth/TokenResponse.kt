package com.martin.core.db.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "accessToken")
    val accessToken: String? = null,
    @Json(name = "refreshToken")
    val refreshToken: String? = null,
)
