package com.example.hilt.data.remote.repository

import com.example.hilt.data.remote.datasource.ReviewRemoteDataSource
import com.example.hilt.data.remote.model.SaveReviewRequestModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewRemoteDataSource
) {
    suspend fun saveReview(
        productId: String,
        rating: Int,
        locale: String,
        comment: String
    ) {
        val requestModel = SaveReviewRequestModel(
            productId = productId,
            locale = locale,
            rating = rating,
            text = comment
        )
        reviewRemoteDataSource.saveReview(productId, requestModel)
    }


    suspend fun getReviewList(id: String) =
        reviewRemoteDataSource.getReviewList(id)
}