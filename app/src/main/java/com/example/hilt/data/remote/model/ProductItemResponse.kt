package com.example.hilt.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ProductItemResponse(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "currency")
    val currency: String?,
    @Json(name = "price")
    val price: String?,
)