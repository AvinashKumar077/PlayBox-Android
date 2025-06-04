package com.martin.features.navigation

import com.martin.core.db.home.VideoModel
import com.martin.core.navigation.HomeRoutes
import com.martin.core.navigation.VideoPlayerEntry
import com.martin.core.utils.MoshiEntityConverter

class VideoPlayerEntryImpl : VideoPlayerEntry {


    override val baseRoute = HomeRoutes.VideoPlayer.route

    override fun route(video: VideoModel): String {
        val json = MoshiEntityConverter.toJson(video)
        return "$baseRoute/$json"
    }
}
