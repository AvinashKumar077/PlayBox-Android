package com.martin.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.martin.core.R
import com.martin.core.db.User
import com.martin.core.db.home.VideoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    HomeScreenContentWithPullToRefresh()
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenContentWithPullToRefresh() {
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    // Dummy list of videos (same dummy object repeated for now)
    val dummyVideo = VideoModel(
        videoFile = "https://example.com/video.mp4",
        thumbnail = "https://i.ytimg.com/vi/5xX5MmmThiU/maxresdefault.jpg",
        title = "Exploring Compose: Full Tutorial",
        description = "Learn Jetpack Compose from scratch in this in-depth tutorial!",
        duration = 12.5,
        views = 12345,
        createdAt = "2025-05-01T10:15:30.000Z",
        owner = User(
            userId = "martin_dev",
            avatar = "https://wallpaperaccess.com/full/861896.jpg"
        )
    )
    var videoList by remember { mutableStateOf(List(5) { dummyVideo }) }

    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(1500)
            videoList = List(videoList.size + 1) { dummyVideo } // Add one more dummy video
            isRefreshing = false
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.betterlogo),
                            contentDescription = "YouTube Logo",
                            modifier = Modifier.height(50.dp)
                        )
                        Text("PlayBox")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Cast, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.4f), // Translucent black
                    scrolledContainerColor = Color.Black.copy(alpha = 0.6f) // Slightly less transparent when scrolled
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            LazyColumn(Modifier.fillMaxSize().background(Color.Black)) {
                items(videoList.size) { index ->
                    VideoCard(video = videoList[index])
                    if (index < videoList.size - 1) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

