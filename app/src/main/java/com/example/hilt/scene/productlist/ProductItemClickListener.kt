package com.example.hilt.scene.productlist

import android.widget.ImageView
import com.example.hilt.scene.uimodel.ProductUIModel

interface ProductItemClickListener {
    fun onItemSelected(imageView: ImageView,uiModel: ProductUIModel)
}