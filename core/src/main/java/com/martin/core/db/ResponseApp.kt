package com.martin.core.db

import com.martin.core.AuthStates
import com.martin.core.SessionManager
import com.martin.core.constants.StatusCode
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@JsonClass(generateAdapter = true)
data class ResponseApp<T>(
    @Json(name = "data")
    val response: T?,
    @Json(name = "success")
    val success: Boolean? = null,
    @Json(name = "statusCode")
    val statusCode: Int? = null,
    @Json(name = "message")
    val message: String? = null,
)

fun <T> ResponseApp<T>.getResponse(): Pair<String?, T?> {
    return when (statusCode) {
        StatusCode.SUCCESS -> {
            Pair(null, response)
        }

        StatusCode.UNAUTHORIZED -> {
            SessionManager.currentAuthState.postValue(AuthStates.UNAUTHORISED)
            Pair(null, null)
        }
        StatusCode.CONFLICT->{
            Pair(message?:"User Already Exists", null)
        }


        else -> Pair(message, null)
    }
}

fun <T> ResponseApp<T>.getResponse(
    onSuccess: (T?) -> Unit,
    onError: ((String?) -> Unit)? = null,
    onUnauthorized: (() -> Unit)? = null
) {
    when (statusCode) {
        StatusCode.SUCCESS -> {
            onSuccess(response)
        }

        StatusCode.UNAUTHORIZED -> {
            SessionManager.currentAuthState.postValue(AuthStates.UNAUTHORISED)
            onUnauthorized?.invoke()
        }

        else -> {
            onError?.invoke(message)
        }
    }
}

//fun <T> flowOfResponseApp(
//    response: T?,
//    status: String? = null,
//    statusCode: Int? = null,
//    message: String? = null,
//): Flow<ResponseApp<T>> {
//    return flowOf(
//        ResponseApp(
//            response, success, statusCode, message
//        )
//    )
//}
//
