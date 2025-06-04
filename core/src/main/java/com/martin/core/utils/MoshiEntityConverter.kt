package com.martin.core.utils

import com.martin.core.db.home.VideoModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URLDecoder
import java.net.URLEncoder

object MoshiEntityConverter {

    private val moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    private val adapter by lazy {
        moshi.adapter(VideoModel::class.java)
    }

    fun toJson(videoModel: VideoModel): String {
        val json = adapter.toJson(videoModel)
        return URLEncoder.encode(json, "UTF-8") // Encode to pass safely in NavController
    }

    fun fromJson(encodedJson: String): VideoModel? {
        val decodedJson = URLDecoder.decode(encodedJson, "UTF-8")
        return adapter.fromJson(decodedJson)
    }



}