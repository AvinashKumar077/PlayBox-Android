package com.martin.core.db.auth

import android.net.Uri

data class SignUpRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val username: String,
    val avatarUri: Uri?,
    val coverImageUri: Uri?
)
