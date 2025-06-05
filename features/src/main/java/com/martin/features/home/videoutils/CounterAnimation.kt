package com.martin.features.home.videoutils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.martin.core.ui.roboto
import kotlinx.coroutines.delay


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RollingDigit(
    targetDigit: Int,
    rollCount: Int,
    durationPerRoll: Int,
) {
    val totalSteps = rollCount
    var animatedDigit by remember(targetDigit) { mutableIntStateOf(0) }

    // Trigger animation only when targetDigit changes
    LaunchedEffect(targetDigit) {
        delay(600)
        val startingDigit = animatedDigit
        val steps = totalSteps.coerceAtLeast(1)
        repeat(steps) {
            animatedDigit = (startingDigit + it + 1) % 10
            delay(durationPerRoll.toLong())
        }
        animatedDigit = targetDigit
    }

    AnimatedContent(
        targetState = animatedDigit,
        transitionSpec = {
            (slideInVertically { it } + fadeIn())
                .togetherWith(slideOutVertically { -it } + fadeOut())
                .using(SizeTransform(clip = false))
        },
        label = "RollingDigitAnimation"
    ) { digit ->
        Text(
            text = digit.toString(),
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = roboto
        )
    }
}

@Composable
fun RollingCounter(
    targetNumber: Int?,
    modifier: Modifier = Modifier,
) {
    if (targetNumber == null) return

    val digits = remember(targetNumber) {
        targetNumber.toString().map { it.digitToInt() }
    }

    Row(modifier = modifier) {
        digits.forEachIndexed { index, digit ->
            val rolls = 6 + index * 3              // Right rolls more
            val speed = 100 + index * 40           // Right is slower

            RollingDigit(
                targetDigit = digit,
                rollCount = rolls,
                durationPerRoll = speed,
            )
        }
    }
}
