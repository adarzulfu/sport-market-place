package com.example.hilt.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SaveReviewRequestModel(
    @Json(name = "productId")
    val productId: String,
    @Json(name = "locale")
    val locale: String,
    @Json(name = "rating")
    val rating: Int,
    @Json(name = "text")
    val text: String
)