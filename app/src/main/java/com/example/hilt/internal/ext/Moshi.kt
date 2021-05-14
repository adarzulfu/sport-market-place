package com.example.hilt.internal.ext

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

inline fun <reified T> Moshi.fromJsonList(json: String?): List<T>? {
    json?.let {
        val types = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter = adapter<List<T>>(types)

        return adapter?.fromJson(it)
    } ?: run {
        return null
    }
}