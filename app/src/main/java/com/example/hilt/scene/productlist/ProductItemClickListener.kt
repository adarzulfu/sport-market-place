package com.example.hilt.scene.productlist

import android.widget.ImageView

interface ProductItemClickListener {
    fun onItemSelected(imageView: ImageView,uiModel: ProductUIModel)
}