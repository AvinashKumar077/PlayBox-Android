package com.martin.core.network

import com.martin.core.db.auth.LoginRequestModel
import com.martin.core.db.auth.LoginResponse
import com.martin.core.db.ResponseApp
import com.martin.core.db.auth.SignUpResponse
import com.martin.core.db.auth.TokenResponse
import com.martin.core.db.comment.LikeModel
import com.martin.core.db.comment.VideoCommentModel
import com.martin.core.db.home.VideoModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ApiService {

    @Multipart
    @POST("/api/v1/users/register")
    suspend fun signUp(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part avatar: MultipartBody.Part?,
        @Part coverImage: MultipartBody.Part?
    ): ResponseApp<SignUpResponse>

    @POST("/api/v1/users/login")
    suspend fun login(
        @Body loginRequest: LoginRequestModel
    ): ResponseApp<LoginResponse>

    @POST("/api/v1/users/refresh-token")
    suspend fun refreshAccessToken(
        @Header("refreshToken") refreshToken: String
    ): TokenResponse

    @GET("api/v1/videos/")
    suspend fun getAllVideos(): ResponseApp<List<VideoModel>>

    @GET("api/v1/videos/{id}")
    suspend fun getVideoById(@Path("id") id: String): ResponseApp<VideoModel>

    @GET("api/v1/comments/{id}")
    suspend fun getAllComments(@Path("id") id: String): ResponseApp<List<VideoCommentModel>>

    @POST("/api/v1/likes/toggle/v/{id}")
    suspend fun toggleVideoLike(@Path("id") id: String): ResponseApp<LikeModel>

    @POST("/api/v1/subscriptions/c/{id}")
    suspend fun toggleSubscription(@Path("id") id: String)
}