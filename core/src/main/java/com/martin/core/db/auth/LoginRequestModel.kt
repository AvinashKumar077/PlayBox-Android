package com.martin.core.db.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestModel(
    @Json(name = "email")
    val email: String?=null,
    @Json(name = "password")
    val password: String?=null
)

