package com.martin.core.db

import android.net.Uri

data class SignUpRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val userName: String,
    val avatarUri: Uri?,
    val coverImageUri: Uri?
)
