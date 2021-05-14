package com.example.hilt.scene.productdetail.rating

interface RatingListener {
    fun onRatingReady(rating: Int, comment: String)
}