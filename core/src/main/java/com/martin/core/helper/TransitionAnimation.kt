package com.martin.core.helper

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

// Helper functions for transitions
fun getSlideFadeTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { 1000 }) +
            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
}

fun getSlideFadeOutTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { -1000 }) +
            fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
}

fun getPopSlideFadeTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { -1000 }) +
            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
}

fun getPopSlideFadeOutTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { 1000 }) +
            fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
}
