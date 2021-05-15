package com.example.hilt.data.remote.datasource

import com.example.hilt.base.BaseRemoteDataSourceTest
import com.example.hilt.data.remote.ProductService
import com.example.hilt.internal.injection.module.NetworkModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.net.HttpURLConnection

class ProductRemoteDataSourceTest : BaseRemoteDataSourceTest() {

    private lateinit var service: ProductService
    private lateinit var remoteDataSource: ProductRemoteDataSource

    override fun setUp() {
        super.setUp()
        service = NetworkModule.provideProductService(retrofit)
        remoteDataSource = ProductRemoteDataSource(service)
    }

    @Test
    fun `when product list requested corresponding list should be return`() = runBlocking {
        // Given
        mockHttpResponse(MOCK_PRODUCT_LIST, HttpURLConnection.HTTP_OK)

        // When
        val result = service.requestGetProductList()

        // Then
        Assert.assertEquals(result.first()?.id, "HBCA1")
        Assert.assertEquals(result.first()?.name, "name")
        Assert.assertEquals(result.first()?.description, "description")
        Assert.assertEquals(result.first()?.currency, "currency")
        Assert.assertEquals(result.first()?.price, 50)
    }

    companion object {
        private val MOCK_PRODUCT_LIST = """
            [
              {
                "id": "HBCA1",
                "name": "name",
                "description": "description",
                "currency": "currency",
                "price": 50
              },
               {
                "id": "HBCA1",
                "name": "name",
                "description": "description",
                "currency": "currency",
                "price": 50
              }
            ]
        """.trimIndent()
    }
}