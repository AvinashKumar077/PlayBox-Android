package com.martin.core.repository

import android.content.Context
import android.util.Log
import com.martin.core.db.LoginRequestModel
import com.martin.core.db.LoginResponse
import com.martin.core.db.ResponseApp
import com.martin.core.db.SignUpRequest
import com.martin.core.db.SignUpResponse
import com.martin.core.db.getResponse
import com.martin.core.network.ApiService
import com.martin.core.utils.extensions.toMultipartParts
import jakarta.inject.Inject
import kotlin.math.log

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