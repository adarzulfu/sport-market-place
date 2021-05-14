package com.example.hilt.scene.productlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUIModel(
    val id: String,
    val groupName: String,
    val productName: String,
    val description: String,
    val price: String
) : Parcelable