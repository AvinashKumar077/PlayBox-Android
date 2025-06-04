package com.martin.core.navigation

import com.martin.core.db.home.VideoModel

interface VideoPlayerEntry {
    fun route(video: VideoModel): String
    val baseRoute: String
}