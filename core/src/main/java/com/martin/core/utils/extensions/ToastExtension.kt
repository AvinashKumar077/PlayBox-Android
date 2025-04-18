package com.martin.core.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.martin.core.UiText

fun Context?.toasty(message: String, isLengthLong: Boolean = false) {
    if (this == null) return
    Toast.makeText(
        this, message, if (isLengthLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()
}

fun Context?.toasty(@StringRes message: Int, isLengthLong: Boolean = false){
    if (this == null) return
    Toast.makeText(
        this, message, if (isLengthLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()
}

fun Context?.toasty(uiText: UiText, isLengthLong: Boolean = false) {
    if (this == null) return
    Toast.makeText(
        this, uiText.get(context = this), if (isLengthLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()
}