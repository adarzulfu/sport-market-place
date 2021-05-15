package com.example.hilt.domain.productlist

import com.example.hilt.base.BaseTest
import com.example.hilt.data.domain.GetProductListUseCase
import com.example.hilt.data.local.model.ImageInfoModel
import com.example.hilt.data.remote.model.ProductItemResponse
import com.example.hilt.data.remote.repository.ProductRepository
import com.example.hilt.internal.util.DummyDataGenerator
import com.example.hilt.internal.util.Either
import com.example.hilt.internal.util.UseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetProductListUseCaseTest : BaseTest() {

    @MockK
    lateinit var productRepository: ProductRepository

    @MockK
    lateinit var dummyDataGenerator: DummyDataGenerator

    @InjectMockKs
    lateinit var getProductListUseCase: GetProductListUseCase


    @Test
    fun `when use case called product list ui model should be returned`() = runBlocking {
        // Given
        coEvery { productRepository.getProductList() } returns MOCK_PRODUCT_LIST_RESPONSE_OBJECT
        every { dummyDataGenerator.dummyImageList } returns MOCK_IMAGE_INFO_LIST

        // When
        val response = getProductListUseCase.run(UseCase.None) as Either.Right

        // Then
        Assert.assertTrue(response.b.size == 3)
        Assert.assertTrue(response.b.first().imageUrl == "https://assets.adidas.com/")
        Assert.assertTrue(response.b.first().id == "1")
    }

    companion object {
        private val MOCK_PRODUCT_LIST_RESPONSE_OBJECT = listOf(
            ProductItemResponse(
                "1",
                "",
                "",
                "",
                0
            ),
            ProductItemResponse(
                "2",
                "",
                "",
                "",
                0
            ), ProductItemResponse(
                "3",
                "",
                "",
                "",
                0
            )
        )
        private val MOCK_IMAGE_INFO_LIST =
            listOf(
                ImageInfoModel("1", "https://assets.adidas.com/"),
                ImageInfoModel("2", "https://assets.adidas.com/"),
                ImageInfoModel("3", "https://assets.adidas.com/")
            )
    }
}