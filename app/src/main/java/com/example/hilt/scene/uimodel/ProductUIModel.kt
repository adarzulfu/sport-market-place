package com.example.hilt.scene.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUIModel(
    val id: String,
    val imageUrl: String,
    val productName: String,
    val description: String,
    val price: String
) : Parcelable