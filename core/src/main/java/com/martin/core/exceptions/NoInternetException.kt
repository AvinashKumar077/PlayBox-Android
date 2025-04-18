package com.martin.core.exceptions

import java.io.IOException

class NoInternetException(
    override val message: String = "No internet connection",
    override val cause: Throwable? = null
): IOException(
    message,
    cause
)