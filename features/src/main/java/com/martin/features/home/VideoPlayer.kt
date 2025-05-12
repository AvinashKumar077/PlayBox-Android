package com.martin.features.home


import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Slider
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.core.net.toUri

@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val activity = context as Activity
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

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
    var showSpeedMenu by rememberSaveable { mutableStateOf(false) }

    val currentPosition = rememberSaveable { mutableLongStateOf(0L) }
    val totalDuration = rememberSaveable { mutableLongStateOf(1L) }

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

    LaunchedEffect(Unit) {
        while (true) {
            currentPosition.longValue = exoPlayer.currentPosition
            totalDuration.longValue = exoPlayer.duration.takeIf { it > 0 } ?: 1L
            kotlinx.coroutines.delay(500)
        }
    }

    BackHandler(enabled = isFullscreen) {
        isFullscreen = false
        toggleFullscreen(activity, false)
    }

    Box(modifier = Modifier.then(
        if (isFullscreen) Modifier.fillMaxSize()
        else Modifier.aspectRatio(16f/9f)
    )) {
        DoubleTapSeekOverlay(
            onSeekForward = { exoPlayer.seekForward() },
            onSeekBackward = { exoPlayer.seekBack() }
        )
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                }
            },
            modifier = Modifier.then(
                if (isFullscreen) Modifier.fillMaxSize().aspectRatio(16f/9f)
                else Modifier.aspectRatio(16f/9f)
            )
        )

        if (isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))

            PlayerSeekBar(
                position = currentPosition.longValue,
                duration = totalDuration.longValue,
                onSeek = { exoPlayer.seekTo(it) }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    isPlaying = !isPlaying
                    if (isPlaying) exoPlayer.play() else exoPlayer.pause()
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = Color.White
                    )
                }

                IconButton(onClick = {
                    isFullscreen = !isFullscreen
                    toggleFullscreen(activity, isFullscreen)
                }) {
                    Icon(
                        imageVector = if (isFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                        contentDescription = "Fullscreen",
                        tint = Color.White
                    )
                }

                IconButton(onClick = { showSpeedMenu = !showSpeedMenu }) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = "Speed",
                        tint = Color.White
                    )
                }

                if (showSpeedMenu) {
                    PlaybackSpeedSelector(currentSpeed = exoPlayer.playbackParameters.speed) {
                        exoPlayer.setPlaybackSpeed(it)
                        showSpeedMenu = false
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerSeekBar(position: Long, duration: Long, onSeek: (Long) -> Unit) {
    Slider(
        value = position.toFloat(),
        onValueChange = { onSeek(it.toLong()) },
        valueRange = 0f..duration.toFloat(),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(4.dp)
    )
}

@Composable
fun DoubleTapSeekOverlay(
    onSeekForward: () -> Unit,
    onSeekBackward: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (it.x < size.width / 2) onSeekBackward() else onSeekForward()
                    }
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
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    } else {
        controller.show(WindowInsetsCompat.Type.systemBars())
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}


fun ExoPlayer.setPlaybackSpeed(speed: Float) {
    this.playbackParameters = this.playbackParameters.withSpeed(speed)
}
