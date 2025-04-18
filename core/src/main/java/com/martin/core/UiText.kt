package com.martin.core

import android.content.Context
import androidx.annotation.StringRes

/**
 *  This class handles the strings, to show on UI.
 * */
sealed class UiText{
    data class ServerString(val value: String) : UiText()
    class LocalStringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    fun get(context: Context): String {
        return when (this) {
            is ServerString -> value
            is LocalStringResource -> context.getString(resId, *args)
        }
    }
}