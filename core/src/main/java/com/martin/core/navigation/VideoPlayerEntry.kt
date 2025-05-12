package com.martin.core.navigation

interface VideoPlayerEntry {
    fun route(videoUrl: String): String
    val baseRoute: String
}