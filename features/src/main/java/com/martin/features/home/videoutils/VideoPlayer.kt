package com.martin.features.home.videoutils

import PlaybackSpeedBottomSheet
import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay

@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val activity = context as Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl.toUri()))
            prepare()
            playWhenReady = true
        }
    }

    var isPlaying by rememberSaveable { mutableStateOf(true) }
    var isBuffering by rememberSaveable { mutableStateOf(true) }
    var isFullscreen by rememberSaveable { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }
    var isSettingsOpen by remember { mutableStateOf(false) }

    val currentPosition = rememberSaveable { mutableLongStateOf(0L) }
    val totalDuration = rememberSaveable { mutableLongStateOf(1L) }

    val showSettingBottomSheet = rememberSaveable { mutableStateOf(false) }
    val playbackSpeed = rememberSaveable { mutableFloatStateOf(1.0f) }

    if (showSettingBottomSheet.value) {
        PlaybackSpeedBottomSheet(
            currentSpeed = playbackSpeed.floatValue,
            onSpeedSelected = {
                playbackSpeed.floatValue = it
                showSettingBottomSheet.value = false
            },
            onDismiss = { showSettingBottomSheet.value = false }
        )
    }
    LaunchedEffect(isBuffering, playbackSpeed.floatValue) {
        delay(200)
        if (!isBuffering) {
            exoPlayer.setPlaybackSpeed(playbackSpeed.floatValue)
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering = state == Player.STATE_BUFFERING
            }
        }
        exoPlayer.addListener(listener)

        val observer = object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                exoPlayer.pause()
            }

            override fun onResume(owner: LifecycleOwner) {
                if (isPlaying) exoPlayer.play()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                exoPlayer.release()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.removeListener(listener)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val updateInterval = if (isBuffering) 1000L else 500L
    LaunchedEffect(Unit) {
        while (true) {
            currentPosition.longValue = exoPlayer.currentPosition
            totalDuration.longValue = exoPlayer.duration.takeIf { it > 0 } ?: 1L
            delay(updateInterval)
        }
    }

    BackHandler(enabled = isFullscreen) {
        isFullscreen = false
        toggleFullscreen(activity, false)
    }

    val videoModifier = if (isFullscreen) Modifier.fillMaxSize() else {
        Modifier
            .windowInsetsPadding(
                WindowInsets.systemBars
            )
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    }
    Box(
        modifier = videoModifier
    ) {
        DoubleTapSeekOverlay(
            onSeekForward = { exoPlayer.seekForward() },
            onSeekBackward = { exoPlayer.seekBack() },
            onTouch = { showControls = !showControls }
        )

        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                }

            },
            modifier = Modifier.fillMaxSize(),
            update = { it.player = exoPlayer }
        )

        if (isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center), color = Color.White,
            )
        }

        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    //play pause button :-
                    PlayPauseButton(isPlaying = isPlaying) {
                        isPlaying = !isPlaying
                        if (isPlaying) exoPlayer.play() else exoPlayer.pause()
                    }

                    //settings button :-
                    RotatingSettingsButton(
                        isRotated = isSettingsOpen,
                        onClick = {
                            isSettingsOpen = !isSettingsOpen
                            showSettingBottomSheet.value=true
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )


                    //fullscreen toggle button:-
                    IconButton(
                        onClick = {
                            isFullscreen = !isFullscreen
                            toggleFullscreen(activity, isFullscreen)
                        },
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            imageVector = if (isFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                            contentDescription = "Fullscreen",
                            tint = Color.White
                        )
                    }
                    CustomSeekBar(
                        currentPosition = currentPosition.longValue,
                        onSeek = { exoPlayer.seekTo(it) },
                        totalDuration = totalDuration.longValue,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomSeekBar(
    currentPosition: Long,
    totalDuration: Long,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color(0xff43b0f1),
    thumbRadius: Dp = 6.dp,
    showTime: Boolean = true
) {
    val thumbRadiusPx = with(LocalDensity.current) { thumbRadius.toPx() }
    var barWidthPx by remember { mutableFloatStateOf(1f) }
    var isDragging by remember { mutableStateOf(false) }
    var dragFraction by remember { mutableFloatStateOf(0f) }

    val progressFraction by remember(currentPosition, totalDuration) {
        derivedStateOf {
            if (totalDuration > 0) {
                currentPosition.toFloat() / totalDuration
            } else 0f
        }
    }

    val visualFraction = if (isDragging) dragFraction else progressFraction

    Column(modifier = modifier) {
        if (showTime) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                Text(
                    "${formatTime((visualFraction * totalDuration).toLong())}/${
                        formatTime(
                            totalDuration
                        )
                    }",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .pointerInput(totalDuration) {
                    detectTapGestures {
                        val tappedFraction = (it.x / barWidthPx).coerceIn(0f, 1f)
                        onSeek((tappedFraction * totalDuration).toLong())
                    }
                }
                .pointerInput(totalDuration) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                            dragFraction = progressFraction
                        },
                        onDragEnd = {
                            isDragging = false
                            onSeek((dragFraction * totalDuration).toLong())
                        },
                        onDragCancel = { isDragging = false },
                        onDrag = { change, _ ->
                            dragFraction = (change.position.x / barWidthPx).coerceIn(0f, 1f)
                        }
                    )
                }
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                        barWidthPx = it.size.width.toFloat()
                    }
                    .drawBehind {
                        drawRoundRect(
                            trackColor,
                            Offset(0f, size.height / 2 - 2f),
                            Size(size.width, 4f),
                            CornerRadius(2f)
                        )
                        drawRoundRect(
                            progressColor,
                            Offset(0f, size.height / 2 - 2f),
                            Size(size.width * visualFraction, 4f),
                            CornerRadius(2f)
                        )
                        drawCircle(
                            progressColor,
                            thumbRadiusPx,
                            Offset(size.width * visualFraction, size.height / 2)
                        )
                    }

            ) {}
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}

@Composable
fun DoubleTapSeekOverlay(
    onSeekForward: () -> Unit,
    onSeekBackward: () -> Unit,
    onTouch: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (it.x < size.width / 2) onSeekBackward() else onSeekForward()
                    },
                    onTap = { onTouch() }
                )
            }
            .zIndex(0f)
    )
}


@Composable
fun PlaybackSpeedSelector(currentSpeed: Float, onSpeedChange: (Float) -> Unit) {
    val speeds = listOf(0.5f, 1f, 1.5f, 2f)
    DropdownMenu(expanded = true, onDismissRequest = {}) {
        speeds.forEach { speed ->
            DropdownMenuItem(onClick = { onSpeedChange(speed) }, text = {
                Text(text = "${speed}x")
            })
        }
    }
}

fun toggleFullscreen(activity: Activity, enable: Boolean) {
    val window = activity.window
    WindowCompat.setDecorFitsSystemWindows(window, !enable)

    val controller = WindowInsetsControllerCompat(window, window.decorView)

    if (enable) {
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    } else {
        controller.show(WindowInsetsCompat.Type.systemBars())
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}


