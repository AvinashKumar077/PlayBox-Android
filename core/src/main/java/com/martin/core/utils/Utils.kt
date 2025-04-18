package com.martin.core.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.martin.core.utils.extensions.toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern



suspend fun <T> withMainContext(
    block: suspend CoroutineScope.() -> T
) {
    withContext(Dispatchers.Main.immediate, block)
}


fun openPermissionSettings(context: Context?) {
    try {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        context?.startActivity(intent)
    } catch (_: Exception) {
        context?.toasty(message = "Failed to open app setting")
    }
}


fun isValidGSTNumber(gstNumber: String): Boolean {
    val gstRegex = "^(0[1-9]|1[0-9]|2[0-9]|3[0-7])[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$"
    val pattern = Pattern.compile(/* regex = */ gstRegex)
    val matcher = pattern.matcher(gstNumber.uppercase())
    return matcher.matches()
}

fun getHintByPosition(
    position: Int,
    isLast: Boolean
): String {
    if (isLast) {
        return "Drop to?"
    }
    return when (position) {
        0 -> {
            "Pickup from?"
        }
        else -> {
            "Stop $position"
        }
    }
}

fun isNotValidEmail(email: String): Boolean {
    return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}



fun minuteToSeconds(minute: Int): Long {
    return TimeUnit.MINUTES.toSeconds(minute.toLong())
}

// mm:ss
fun formatMinAndSec(seconds: Long): String {
    val min = seconds / 60
    val sec = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", min, sec)
}


fun isFirstItem(position: Int): Boolean {
    return position == 0
}

fun isLastItem(totalStops: Int, position: Int): Boolean {
    return totalStops - 1 == position
}

fun validateName(name: String): Boolean {
    return name.length >= 3 && name.count { it.isLetter() } >= 3
}