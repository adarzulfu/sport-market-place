package com.example.hilt.data.repository

import com.example.hilt.base.BaseTest
import com.example.hilt.data.remote.datasource.ReviewRemoteDataSource
import com.example.hilt.data.remote.model.SaveReviewRequestModel
import com.example.hilt.data.remote.repository.ReviewRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ReviewRepositoryTest : BaseTest() {

    @MockK
    lateinit var reviewRemoteDataSource: ReviewRemoteDataSource

    @InjectMockKs
    lateinit var reviewRepository: ReviewRepository

    override fun setUp() {
        coEvery {
            reviewRemoteDataSource.saveReview(any(), any())
        }  returns Unit
    }

    @Test
    fun `when review saved repository should be called`() = runBlocking {
        // Given

        // When
        reviewRepository.saveReview(SaveReviewRequestModel("", "", 1, ""))

        // Then
        coVerify { reviewRemoteDataSource.saveReview(any(), any()) }
    }
}