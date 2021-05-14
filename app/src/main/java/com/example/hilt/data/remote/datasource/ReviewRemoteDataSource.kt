package com.example.hilt.data.remote.datasource

import com.example.hilt.data.remote.ReviewService
import com.example.hilt.data.remote.model.SaveReviewRequestModel
import com.example.hilt.internal.util.BaseRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRemoteDataSource @Inject constructor(private val reviewService: ReviewService) :
    BaseRemoteDataSource() {

    suspend fun saveReview(id: String, reviewRequestModel: SaveReviewRequestModel) = invoke {
        reviewService.requestSaveReview(id, reviewRequestModel)
    }

    suspend fun getReviewList(id: String) = invoke {
        reviewService.requestGetReviewList(id)
    }
}