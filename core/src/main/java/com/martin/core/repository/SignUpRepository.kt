package com.martin.core.repository

import android.content.Context
import com.martin.core.db.ResponseApp
import com.martin.core.db.SignUpRequest
import com.martin.core.db.SignUpResponse
import com.martin.core.network.ApiService
import com.martin.core.utils.extensions.toMultipartParts
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun signUp(formData: SignUpRequest , context: Context): ResponseApp<SignUpResponse>{
        val (dataMap,avatarPart,coverImagePart) = formData.toMultipartParts(context)
        return apiService.signUp(dataMap,avatarPart,coverImagePart)
    }

}