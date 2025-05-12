package com.martin.features.navigation

import com.martin.core.navigation.HomeRoutes
import com.martin.core.navigation.VideoPlayerEntry
import java.net.URLEncoder

class VideoPlayerEntryImpl : VideoPlayerEntry {
    override val baseRoute = HomeRoutes.VideoPlayer.route

    override fun route(videoUrl: String): String {
        val encoded = URLEncoder.encode(videoUrl, "UTF-8")
        return "video_player/$encoded"
    }
}