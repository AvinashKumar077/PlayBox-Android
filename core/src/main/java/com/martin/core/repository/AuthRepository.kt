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

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun signUp(formData: SignUpRequest, context: Context): Pair<String?, SignUpResponse?> {
        return try {
            val (dataMap, avatarPart, coverImagePart) = formData.toMultipartParts(context)
            Log.d("SignUp Request", "data: $dataMap \navatar: $avatarPart \ncover: $coverImagePart")

            val response = apiService.signUp(dataMap, avatarPart, coverImagePart)
            Log.d("SignUp API Raw Response", response.toString())

            response.getResponse()
        } catch (e: Exception) {
            Log.e("SignUp Repository Error", "Failed to signup", e)
            throw e // rethrow so ViewModel can handle it too
        }
    }

    suspend fun logIn(data: LoginRequestModel): Pair<String?, LoginResponse?>{
        return apiService.login(data).getResponse()
    }

}