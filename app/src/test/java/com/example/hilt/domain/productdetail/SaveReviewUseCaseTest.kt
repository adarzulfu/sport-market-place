package com.example.hilt.domain.productdetail

import com.example.hilt.base.BaseTest
import com.example.hilt.data.domain.SaveReviewUseCase
import com.example.hilt.data.remote.repository.ReviewRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test


class SaveReviewUseCaseTest : BaseTest() {

    @MockK
    lateinit var reviewRepository: ReviewRepository

    @InjectMockKs
    lateinit var saveReviewUseCase: SaveReviewUseCase

    @Test
    fun `when use case called corresponding repository should be called`() {
        runBlocking {
            // Given
            coEvery { reviewRepository.saveReview(any(), any(), any(), any()) } returns Unit

            // When
            saveReviewUseCase.run(
                SaveReviewUseCase.Params(
                    "", 5, "",
                    ""
                )
            )

            // Then
            coVerify { reviewRepository.saveReview(any(), any(), any(), any()) }
        }
    }
}