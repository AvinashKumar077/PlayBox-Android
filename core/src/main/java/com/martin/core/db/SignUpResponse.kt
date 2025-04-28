package com.martin.core.db

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponse(
   @Json(name = "user")
   val user: User?=null
)
