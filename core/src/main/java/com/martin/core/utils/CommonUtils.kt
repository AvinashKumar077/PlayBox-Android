package com.martin.core.utils

fun Long?.isNullOrZero(): Boolean {
    return this == null || this == 0L
}

fun Int?.isNullOrZero(): Boolean {
    return this == null || this == 0
}

fun Long?.isNotNullOrZero(): Boolean {
    return this != null && this != 0L
}
inline fun onlyTry(callback: () -> Unit) {
    try {
        callback.invoke()
    } catch (_: Exception) { }
}