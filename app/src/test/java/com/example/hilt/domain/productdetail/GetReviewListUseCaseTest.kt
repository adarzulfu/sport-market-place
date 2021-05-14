package com.example.hilt.domain.productdetail

import com.example.hilt.base.BaseTest
import com.example.hilt.data.domain.GetReviewListUseCase
import com.example.hilt.data.remote.model.ReviewSingleItemResponseModel
import com.example.hilt.data.remote.repository.ReviewRepository
import com.example.hilt.internal.util.Either
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetReviewListUseCaseTest : BaseTest() {

    @MockK
    lateinit var reviewRepository: ReviewRepository

    @InjectMockKs
    lateinit var getReviewListUseCase: GetReviewListUseCase

    override fun setUp() {
        coEvery {
            reviewRepository.getReviewList(any())
        } returns mockList
    }

    @Test
    fun `check if result success`() {
        runBlocking {
            // Given

            // When
            val result = getReviewListUseCase.run(GetReviewListUseCase.Params(""))

            // Then
            Assert.assertTrue(result.isRight)
        }
    }


    @Test
    fun `when review list requested, correct data should return`() {
        runBlocking {
            // Given

            // When
            val result = getReviewListUseCase.run(GetReviewListUseCase.Params(""))
                    as Either.Right
            val actual = result.b.first()
            // Then
            Assert.assertTrue(actual.comment == "hello")
        }
    }

    private val mockList = listOf(
        ReviewSingleItemResponseModel(
            null,
            null,
            null,
            "hello"
        )
    )
}