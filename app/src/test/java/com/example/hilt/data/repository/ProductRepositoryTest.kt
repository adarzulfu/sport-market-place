package com.example.hilt.data.repository

import com.example.hilt.base.BaseTest
import com.example.hilt.data.remote.datasource.ProductRemoteDataSource
import com.example.hilt.data.remote.model.ProductItemResponse
import com.example.hilt.data.remote.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert

import org.junit.Test

class ProductRepositoryTest : BaseTest() {

    @MockK
    lateinit var productDataSource: ProductRemoteDataSource

    @InjectMockKs
    lateinit var productRepository: ProductRepository

    @Test
    fun `when get product list called should return desired object`() {
        runBlocking {
            // Given
            coEvery { productDataSource.requestProductList() } returns MOCK_PRODUCT_LIST_RESPONSE_OBJECT

            // When
            val result = productRepository.getProductList()

            // Then
            Assert.assertEquals(result, MOCK_PRODUCT_LIST_RESPONSE_OBJECT)
        }
    }


    companion object {
        val MOCK_PRODUCT_LIST_RESPONSE_OBJECT = listOf(
            ProductItemResponse(
                "1",
                "",
                "",
                "",
                0
            )
        )
    }

}