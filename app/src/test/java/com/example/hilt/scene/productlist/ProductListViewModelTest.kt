package com.example.hilt.scene.productlist

import com.example.hilt.base.BaseTest
import com.example.hilt.data.domain.GetProductListUseCase
import com.example.hilt.internal.util.Either
import com.example.hilt.scene.uimodel.ProductUIModel
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Assert

import org.junit.Test

class ProductListViewModelTest : BaseTest() {

    @MockK
    lateinit var getProductListUseCase: GetProductListUseCase

    @InjectMockKs
    lateinit var productListViewModel: ProductListViewModel

    override fun setUp() {
        super.setUp()
        coEvery { getProductListUseCase.run(any()) } returns Either.Right(MOCK_RESPONSE)
    }


    @Test
    fun `when load retry requested the livedata should not be empty `() {
        // Given

        // When
        productListViewModel.retryRequested()

        // Then
        Assert.assertEquals(productListViewModel.productListLiveData.value, MOCK_RESPONSE)
    }

    @Test
    fun `when load product list called the livedata should not be empty `() {
        // Given

        // When
        productListViewModel.loadProductList()

        // Then
        Assert.assertEquals(productListViewModel.productListLiveData.value, MOCK_RESPONSE)
    }

    @Test
    fun `when load product list called progress live data should be triggered`() {
        // Given

        // When
        productListViewModel.loadProductList()

        // Then
        Assert.assertNotNull(productListViewModel.progressLiveData.value)
    }

    @Test
    fun `when a search query is typed list should be filtered by product name`() {
        // Given
        productListViewModel.loadProductList()

        // When
        productListViewModel.onQueryTextChange("test1")

        // Then
        Assert.assertTrue(productListViewModel.productListLiveData.value?.size == 1)
    }

    @Test
    fun `when a search query is typed list should be filtered by description name`() {
        // Given
        productListViewModel.loadProductList()

        // When
        productListViewModel.onQueryTextChange("test1Description")

        // Then
        Assert.assertTrue(productListViewModel.productListLiveData.value?.size == 1)
    }

    @Test
    fun `when a search query is typed list should be filtered by price name`() {
        // Given
        productListViewModel.loadProductList()

        // When
        productListViewModel.onQueryTextChange("50")

        // Then
        Assert.assertTrue(productListViewModel.productListLiveData.value?.size == 3)
    }

    companion object {
        private val MOCK_RESPONSE = listOf(
            ProductUIModel(
                "1",
                "imageUrl",
                "test1",
                "test1Description",
                "$50"
            ), ProductUIModel(
                "2",
                "imageUrl",
                "test2",
                "test2Description",
                "$50"
            ), ProductUIModel(
                "3",
                "imageUrl",
                "test3",
                "test3Description",
                "$50"
            ), ProductUIModel(
                "4",
                "imageUrl",
                "test4",
                "test4Description",
                "$60"
            )
        )
    }
}