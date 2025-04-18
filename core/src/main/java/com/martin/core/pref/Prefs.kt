package com.martin.core.pref

import android.content.Context
import android.content.SharedPreferences
import com.martin.core.db.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

private const val PREF_FILE_NAME = "com.martin.playbox.prefs"
private const val PERMANENT_PREFS = "com.martin.rider.playbox.permanent_prefs"
private const val TOKEN = "access_token"
private const val REFRESH = "refresh_token"

interface Prefs {
    var mAccessToken: String?
    var mRefreshToken: String
    fun getAccessToken(): String?
    fun updateSecurityToken(tokenResponse: TokenResponse? = null)
    fun clear()
    fun getSharedPrefs(): SharedPreferences
    fun getPermanentSharedPrefs(): SharedPreferences
}

@Singleton
class PrefImpl @Inject constructor(
    private val context: Context,
) : Prefs {

    private val prefs: SharedPreferences = getSharedPrefs()
    private val permanentPrefs: SharedPreferences by lazy { getPermanentSharedPrefs() }

    override fun getSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun getPermanentSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(PERMANENT_PREFS, Context.MODE_PRIVATE)
    }

    override var mAccessToken: String?
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit { putString(TOKEN, value) }

    override fun getAccessToken(): String? {
        return mAccessToken?.takeIf { it.isNotEmpty() }?.let {
            if (it.startsWith("Bearer ")) it else "Bearer $it"
        }
    }

    override var mRefreshToken: String
        get() = prefs.getString(REFRESH, "") ?: ""
        set(value) = prefs.edit { putString(REFRESH, value) }

    override fun updateSecurityToken(tokenResponse: TokenResponse?) {
        tokenResponse?.accessToken?.let { mAccessToken = it }
        tokenResponse?.refreshToken?.let { mRefreshToken = it }
    }

    override fun clear() {
        prefs.edit { clear() }
    }
}
