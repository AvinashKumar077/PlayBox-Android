package com.martin.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.martin.core.db.home.VideoModel

@Composable
fun VideoPlayerScreen(videoUrl: String?){
    Column(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(videoUrl.toString())
        Column(Modifier.weight(1f)) {
            Text(text = "This is the Content")
        }
    }
}