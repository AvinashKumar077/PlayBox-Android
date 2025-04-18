package com.martin.core.utils.extensions

import com.martin.core.utils.withMainContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchSafe(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return this.launch(context = context) {
        try {
            block()
        } catch (e: Exception) {

        }
    }
}

fun CoroutineScope.launchSafeWithErrorHandling(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
    onError: (Exception) -> Unit
): Job {
    return this.launch(context) {
        try {
            block()
        } catch (e: Exception) {
            withMainContext {
                onError(e)
            }
        }
    }
}

fun <T> CoroutineScope.asyncSafe (
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> T
): Deferred<T?> {
    return async(
        context = context,
        start = CoroutineStart.DEFAULT,
        block = {
            try {
                block()
            } catch (e: Exception) {
                null
            }
        }
    )
}