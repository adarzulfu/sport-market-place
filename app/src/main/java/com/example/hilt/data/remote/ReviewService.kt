package com.example.hilt.data.remote

import com.example.hilt.data.remote.model.ReviewSingleItemResponseModel
import com.example.hilt.data.remote.model.SaveReviewRequestModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {

    @GET(REVIEW)
    suspend fun requestGetReviewList(@Path("id") id: String)
            : List<ReviewSingleItemResponseModel?>?

    @POST(REVIEW)
    suspend fun requestSaveReview(
        @Path("id") id: String,
        @Body requestModel: SaveReviewRequestModel
    )

    companion object {
        const val REVIEW = "/reviews/{id}"
    }
}