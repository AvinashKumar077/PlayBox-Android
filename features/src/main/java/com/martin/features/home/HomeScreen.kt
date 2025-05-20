package com.martin.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.martin.core.R
import com.martin.features.home.videoutils.VideoCard
import com.martin.features.navigation.VideoPlayerEntryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController,bottomPadding: PaddingValues,viewModel: HomeViewModel = hiltViewModel()) {
    HomeScreenContentWithPullToRefresh(navController,bottomPadding,viewModel)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenContentWithPullToRefresh(navController: NavController,bottomPadding: PaddingValues,viewModel: HomeViewModel) {
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val videos by viewModel.videoList.collectAsStateWithLifecycle()

    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            viewModel.getAllVideos()
            delay(1500)
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
                    containerColor = Color.Black.copy(alpha = 0.8f), // Translucent black
                    scrolledContainerColor = Color.Black // Slightly less transparent when scrolled
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier,
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            LazyColumn(Modifier.fillMaxSize().background(Color.Black)) {
                item {
                    Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                }
                videos?.let { list ->
                    items(list.size) { index ->
                        val videoPlayerEntry = VideoPlayerEntryImpl()
                        VideoCard(
                            video = list[index],
                            onClick = {
                                navController.navigate(
                                    videoPlayerEntry.route(list[index].videoFile.toString())
                                )
                            }
                        )
                        if (index < list.size - 1) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(bottomPadding.calculateBottomPadding()))
                }
            }

        }
    }
}

