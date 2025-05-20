package com.martin.features.home.playerscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.martin.features.home.videoutils.VideoPlayer


@Composable
fun VideoPlayerScreen(videoUrl: String?){
    Column(modifier = Modifier.fillMaxSize()){
        VideoPlayer(videoUrl.toString())
        Column(Modifier.weight(1f)) {

        }
    }
}