package com.martin.core.utils.extensions

import com.martin.core.db.ResponseApp
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


inline fun <reified T> Moshi.toJsonForList(value: List<T>?): String {
    return this.adapter<List<T>>(
        Types.newParameterizedType(
            List::class.java,
            T::class.java
        ), emptySet()
    ).toJson(value)
}

inline fun <reified T> Moshi.fromJsonForList(value: String): List<T>? {
    return this.adapter<List<T>>(
        Types.newParameterizedType(
            List::class.java,
            T::class.java
        ), emptySet()
    ).fromJson(value)
}


inline fun <reified K, reified V> Moshi.toJsonForMap(value: Map<K, V>?): String {
    return this.adapter<Map<K, V>>(
        Types.newParameterizedType(
            Map::class.java,
            K::class.java,
            V::class.java
        ), emptySet()
    ).toJson(value)
}

inline fun <reified K, reified V> Moshi.fromJsonForMap(
    value: String
): Map<K, V>? {
    return this.adapter<Map<K, V>>(
        Types.newParameterizedType(
            Map::class.java,
            K::class.java,
            V::class.java
        ), emptySet()
    ).fromJson(value)
}

inline fun <reified T> Moshi.fromJsonForResponseApp(json: String): ResponseApp<T>? {
    return this.adapter<ResponseApp<T>>(
        Types.newParameterizedType(
            ResponseApp::class.java,
            T::class.java
        )
    ).fromJson(json)
}