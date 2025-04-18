package com.martin.core.db

import com.squareup.moshi.Json

data class SignUpResponse(
    @Json(name = "_id")
    val userId: String? = null,
    @Json(name = "userName")
    val userName: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "fullName")
    val fullName: String? = null,
    @Json(name = "avatar")
    val avatar: String? = null,
    @Json(name = "coverImage")
    val coverImage: String? =null,
    @Json(name = "accessToken")
    val accessToken: String? = null,
    @Json(name = "refreshToken")
    val refreshToken: String? = null,
)
