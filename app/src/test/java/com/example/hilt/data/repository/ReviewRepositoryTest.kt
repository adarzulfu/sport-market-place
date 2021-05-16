package com.example.hilt.data.repository

import com.example.hilt.base.BaseTest
import com.example.hilt.data.remote.datasource.ReviewRemoteDataSource
import com.example.hilt.data.remote.model.ReviewSingleItemResponseModel
import com.example.hilt.data.remote.repository.ReviewRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ReviewRepositoryTest : BaseTest() {

    @MockK
    lateinit var reviewRemoteDataSource: ReviewRemoteDataSource

    @InjectMockKs
    lateinit var reviewRepository: ReviewRepository

    @Test
    fun `when review saved corresponding repository should be called`() = runBlocking {
        // Given

        coEvery {
            reviewRemoteDataSource.saveReview(any(), any())
        } returns Unit

        // When
        reviewRepository.saveReview("", 0, "", "")

        // Then
        coVerify { reviewRemoteDataSource.saveReview(any(), any()) }
    }

    @Test
    fun `when review list requested expected result should return`() = runBlocking {
        // Given
        coEvery {
            reviewRemoteDataSource.getReviewList(any())
        } returns MOCK_REVIEW_LIST
        // When
        val result = reviewRepository.getReviewList("1")

        // Then
        Assert.assertEquals(result, MOCK_REVIEW_LIST)
    }

    companion object {
        private val MOCK_REVIEW_LIST = listOf(
            ReviewSingleItemResponseModel(
                "", "", 0, ""
            )
        )
    }
}