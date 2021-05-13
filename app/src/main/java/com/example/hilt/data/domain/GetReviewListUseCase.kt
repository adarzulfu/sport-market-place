package com.example.hilt.data.domain

import com.example.hilt.data.remote.model.ReviewSingleItemResponseModel
import com.example.hilt.data.remote.repository.ReviewRepository
import com.example.hilt.internal.util.UseCase
import com.example.hilt.scene.productdetail.ReviewUIModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetReviewListUseCase @Inject constructor(private val reviewRepository: ReviewRepository) :
    UseCase<List<ReviewUIModel>, GetReviewListUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): List<ReviewUIModel> {
        val response = reviewRepository.getReviewList(params.productId)
        return convertReviewToUiModel(response)
    }

    private fun convertReviewToUiModel(response: List<ReviewSingleItemResponseModel?>?)
            : List<ReviewUIModel> {
        return response?.mapNotNull {
            ReviewUIModel(it?.rating ?: -1, it?.text ?: "")
        } ?: listOf()
    }

    class Params(val productId: String)
}