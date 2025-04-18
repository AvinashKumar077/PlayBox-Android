package com.martin.core.network

import com.martin.core.db.ResponseApp
import com.martin.core.db.SignUpRequest
import com.martin.core.db.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiService {

    @Multipart
    @POST("/api/v1/users/register")
    suspend fun signUp(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part avatar: MultipartBody.Part?,
        @Part coverImage: MultipartBody.Part?
    ):ResponseApp<SignUpResponse>

}