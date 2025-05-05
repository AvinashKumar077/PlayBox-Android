package com.martin.core.network

import com.martin.core.helper.TokenRefresher
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val prefs: Prefs,
    private val prefUtils: PrefUtils,
    private val tokenRefresher: TokenRefresher
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null

        val newAccessToken = runBlocking {
            tokenRefresher.refreshToken()
        }

        return newAccessToken?.let {
            response.request.newBuilder()
                .header("Authorization", it)
                .build()
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
