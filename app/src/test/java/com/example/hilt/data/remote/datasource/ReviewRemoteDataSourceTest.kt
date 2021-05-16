package com.example.hilt.data.remote.datasource

import com.example.hilt.base.BaseRemoteDataSourceTest
import com.example.hilt.data.remote.ReviewService
import com.example.hilt.internal.injection.module.NetworkModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.net.HttpURLConnection

class ReviewRemoteDataSourceTest : BaseRemoteDataSourceTest() {

    private lateinit var service: ReviewService
    private lateinit var remoteDataSource: ReviewRemoteDataSource

    override fun setUp() {
        super.setUp()
        service = NetworkModule.provideReviewServiceService(retrofit)
        remoteDataSource = ReviewRemoteDataSource(service)
    }

    @Test
    fun `when request review list service should return it`() {
        runBlocking {
            // Given
            mockHttpResponse(MOCK_RESPONSE, HttpURLConnection.HTTP_OK)

            // When
            val response = remoteDataSource.getReviewList("id")

            // Then
            Assert.assertTrue(response?.size == 3)
            Assert.assertTrue(response?.first()?.productId == "HI333")
            Assert.assertTrue(response?.first()?.rating == 0)
        }
    }

    companion object {
        var MOCK_RESPONSE = """[
            {
                "productId": "HI333",
                "locale": "tr,en;q=0.9",
                "rating": 0,
                "text": "text"
            },
            {
                "productId": "HI333",
                "locale": "tr,en;q=0.9",
                "rating": 0,
                "text": "string"
            },
            {
                "productId": "HI333",
                "locale": "tr,en;q=0.9",
                "rating": 0,
                "text": "string"
            }
        ]""".trimIndent()
    }
}

