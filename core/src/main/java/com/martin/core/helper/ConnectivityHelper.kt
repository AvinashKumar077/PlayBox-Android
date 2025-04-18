package com.martin.core.helper

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val exceptionMessage = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        // Check if the network is validated
        if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            return false
        }

        // Ensure we have internet capability
        if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            return false
        }
        return true
    }

    @OptIn(FlowPreview::class)
    fun getExceptionMessageFlow(): Flow<String> {
        return exceptionMessage.debounce(1000)
    }

    fun showExceptionMessage(message: String) {
        exceptionMessage.tryEmit(message)
    }
}