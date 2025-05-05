package com.martin.core.network

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.annotation.RequiresPermission
import com.martin.core.exceptions.NoInternetException
import com.martin.core.helper.ConnectivityHelper
import com.martin.core.helper.TokenRefresher
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val prefs: Prefs,
    private val prefUtils: PrefUtils,
    private val connectivityHelper: ConnectivityHelper,
) : Interceptor {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Check internet connectivity
        if (!connectivityHelper.isConnected()) {
            connectivityHelper.showExceptionMessage("No internet connection")
            throw NoInternetException(
                cause = Throwable(
                    request.url.toString().substringAfter(".com")
                )
            )
        }

        // Prepare request with access and refresh tokens
        val originalRequestBuilder = request.newBuilder()
            .header("Content-Type", "application/json")
            .header("User-Agent", "MyApp/1.0 (Android ${Build.VERSION.RELEASE})")

        prefs.getAccessToken()?.let {
            originalRequestBuilder.header("Authorization", it)
        }

        prefs.mRefreshToken?.let {
            originalRequestBuilder.header("refreshToken", it)
        }

        request = originalRequestBuilder.build()

        return try {
            chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            throw IOException(e.message)
        } catch (e: UnknownHostException) {
            throw NoInternetException(
                cause = Throwable(
                    request.url.toString().substringAfter(".com")
                )
            )
        }
    }
}

