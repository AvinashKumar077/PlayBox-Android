package com.martin.core.helper

import android.util.Log
import com.martin.core.network.ApiService
import com.martin.core.pref.Prefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRefresher @Inject constructor(
    @RefreshClient private val apiService: ApiService,
    private val prefs: Prefs
) {
    suspend fun refreshToken(): String? {
        val refreshToken = prefs.mRefreshToken ?: return null
        return try {
            val response = apiService.refreshAccessToken(refreshToken)
            prefs.updateSecurityToken(response)
            response.accessToken
        } catch (e: Exception) {
            Log.e("TokenRefresher", "Token refresh failed", e)
            null
        }
    }
}
