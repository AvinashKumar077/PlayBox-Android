package com.martin.core.network

import android.Manifest
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.annotation.RequiresPermission
import com.martin.core.exceptions.NoInternetException
import com.martin.core.helper.ConnectivityHelper
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.userAgent
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val prefs: Prefs,
    private val prefUtils: PrefUtils,
    private val connectivityHelper: ConnectivityHelper,
) : Interceptor{
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!connectivityHelper.isConnected()) {
            connectivityHelper.showExceptionMessage(message = "No internet connection")
            throw NoInternetException(cause = Throwable(message = request.url.toString().substringAfter(".com")))
        }

        val requestBuilder = request.newBuilder()
            .header("Content-Type", "application/json")
            .header("User-Agent", userAgent)

        prefs.getAccessToken()?.let {
            requestBuilder.header("Authorization", it)
        }

        if (prefs.mRefreshToken.isNotEmpty()) {
            requestBuilder.header("refreshToken", prefs.mRefreshToken)
        }
        request = requestBuilder.build()
        return try {
            chain.proceed(request)
        } catch (e: HttpException) {
            throw IOException(e.message)
        } catch (e: SocketTimeoutException) {
            throw IOException(e.message)
        } catch (e: UnknownHostException) {
            throw NoInternetException(cause = Throwable(message = request.url.toString().substringAfter(".com")))
        }
    }
}