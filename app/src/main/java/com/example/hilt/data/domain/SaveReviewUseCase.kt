package com.example.hilt.data.domain

import com.example.hilt.data.remote.model.SaveReviewRequestModel
import com.example.hilt.data.remote.repository.ReviewRepository
import com.example.hilt.internal.util.UseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveReviewUseCase @Inject constructor(private val reviewRepository: ReviewRepository) :
    UseCase<Unit, SaveReviewUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) {
        val requestModel =
            SaveReviewRequestModel(
                productId = params.productId,
                locale = params.locale,
                rating = params.rating,
                text = params.comment
            )
        reviewRepository.saveReview(requestModel)
    }

    class Params(
        val productId: String,
        val rating: Int,
        val locale: String,
        val comment: String
    )
}