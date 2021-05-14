package com.example.hilt.data.local.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ImageInfoModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "img")
    val img: String
)