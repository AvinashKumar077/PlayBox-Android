package com.martin.core.pref

import android.content.Context
import com.martin.core.db.User
import com.martin.core.utils.isNullOrZero
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface PrefUtils {
    fun getUserDetails(): User?
    fun updateUser(user: User)
    fun clearAll()

@Singleton
class PrefUtilsImpl @Inject constructor(
    private val prefs: Prefs,
    @ApplicationContext private val context: Context
): PrefUtils {
    private var user: User? = null

    override fun getUserDetails(): User? {
        if (user?.userId.isNullOrEmpty()) {
            this.user = prefs.user
        }
        return user
    }

    override fun updateUser(user: User) {
        prefs.updateUser(user)
        this.user = user
    }

    override fun clearAll() {
        user = null
    }
}
}