package com.example.hilt.data.domain

import com.example.hilt.data.remote.repository.ReviewRepository
import com.example.hilt.internal.util.UseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveReviewUseCase @Inject constructor(private val reviewRepository: ReviewRepository) :
    UseCase<Unit, SaveReviewUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) {
        reviewRepository.saveReview(
            productId = params.productId,
            locale = params.locale,
            rating = params.rating,
            comment = params.comment
        )
    }

    class Params(
        val productId: String,
        val rating: Int,
        val locale: String,
        val comment: String
    )
}