package com.martin.core.repository

import android.content.Context
import com.martin.core.db.auth.LoginRequestModel
import com.martin.core.db.auth.LoginResponse
import com.martin.core.db.auth.SignUpRequest
import com.martin.core.db.auth.SignUpResponse
import com.martin.core.db.getResponse
import com.martin.core.network.ApiService
import com.martin.core.utils.extensions.toMultipartParts
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun signUp(formData: SignUpRequest, context: Context): Pair<String?, SignUpResponse?> {
        return try {
            val (dataMap, avatarPart, coverImagePart) = formData.toMultipartParts(context)
            val response = apiService.signUp(dataMap, avatarPart, coverImagePart)

            response.getResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun logIn(data: LoginRequestModel): Pair<String?, LoginResponse?>{
        return apiService.login(data).getResponse()
    }

}