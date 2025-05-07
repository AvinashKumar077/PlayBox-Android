package com.martin.core.helper

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun getTimeAgo(dateString: String): String {
    // Example: 2025-04-08T16:03:45.897Z (ISO 8601)
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")

    val time = try {
        format.parse(dateString)?.time ?: return "Unknown time"
    } catch (e: Exception) {
        return "Invalid time"
    }

    val now = System.currentTimeMillis()
    val diff = now - time

    val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        seconds < 60 -> "Just now"
        minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
        hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
        days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
        else -> {
            val date = Date(time)
            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
        }
    }
}
