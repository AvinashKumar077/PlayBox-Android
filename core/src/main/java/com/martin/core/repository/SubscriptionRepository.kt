package com.martin.core.repository

import android.util.Log
import com.martin.core.network.ApiService
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun toggleSubscription(channelId: String) {
        apiService.toggleSubscription(channelId)
    }
}