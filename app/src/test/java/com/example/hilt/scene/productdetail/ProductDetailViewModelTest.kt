package com.example.hilt.scene.productdetail

import com.example.hilt.base.BaseTest
import com.example.hilt.data.domain.GetReviewListUseCase
import com.example.hilt.data.domain.SaveReviewUseCase
import com.example.hilt.internal.util.Either
import com.example.hilt.scene.uimodel.ReviewUIModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import org.junit.Assert
import org.junit.Test

class ProductDetailViewModelTest : BaseTest() {

    @MockK
    lateinit var getReviewListUseCase: GetReviewListUseCase

    @MockK
    lateinit var saveReviewUseCase: SaveReviewUseCase

    @InjectMockKs
    lateinit var productDetailViewModel: ProductDetailViewModel

    override fun setUp() {

        coEvery {
            saveReviewUseCase.run(any())
        } returns Either.Right(Unit)

        coEvery {
            getReviewListUseCase.run(any())
        } returns Either.Right(mockProductUiList)
    }

    @Test
    fun `when loadReviews called reviewListLiveData should be field`() {
        // Given

        // When
        productDetailViewModel.loadReviews("")

        // Then
        Assert.assertNotNull(productDetailViewModel.reviewListLiveData.value)
    }

    @Test
    fun `when loadReviews called getReviewListUseCase should be triggered`() {
        // Given

        // When
        productDetailViewModel.loadReviews("")

        // Then
        coVerify { getReviewListUseCase.run(any()) }
    }

    @Test
    fun `when saveProductId and saveReview called productId param should not be empty`() {
        // Given
        val productId = "1234"
        val slot = slot<SaveReviewUseCase.Params>()
        coEvery {
            saveReviewUseCase.run(capture(slot))
        } returns Either.Right(Unit)

        // When
        productDetailViewModel.saveProductId(productId)
        productDetailViewModel.saveReview(0, "")

        // Then
        Assert.assertTrue(slot.captured.productId == productId)
    }

    @Test
    fun `when saveReview called saveReviewUseCase should be triggered`() {
        // Given

        // When
        productDetailViewModel.saveReview(0, "")

        // Then
        coVerify { saveReviewUseCase.run(any()) }
    }

    private val mockProductUiList = listOf(
        ReviewUIModel(0, "")
    )
}