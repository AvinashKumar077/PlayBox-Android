package com.martin.core.helper

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    fun getTimeAgo(dateString: String): String {
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
            minutes < 60 -> "$minutes minute${if (minutes != 1L) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours != 1L) "s" else ""} ago"
            days < 7 -> "$days day${if (days != 1L) "s" else ""} ago"
            days < 30 -> {
                val weeks = days / 7
                "$weeks week${if (weeks != 1L) "s" else ""} ago"
            }
            days < 365 -> {
                val months = days / 30
                "$months month${if (months != 1L) "s" else ""} ago"
            }
            else -> {
                val years = days / 365
                "$years year${if (years != 1L) "s" else ""} ago"
            }
        }
    }

    fun getFormattedDateForDisplay(dateString: String): Pair<String, String> {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = inputFormat.parse(dateString) ?: return "Invalid" to "Date"
            val dayMonthFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
            val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            dayMonthFormat.format(date) to yearFormat.format(date)
        } catch (e: Exception) {
            "Invalid" to "Date"
        }
    }
}
