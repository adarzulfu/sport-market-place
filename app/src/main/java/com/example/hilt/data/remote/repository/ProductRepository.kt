package com.example.hilt.data.remote.repository

import com.example.hilt.data.remote.datasource.ProductRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productRemoteDataSource:
    ProductRemoteDataSource
) {
    suspend fun getProductList() =
        productRemoteDataSource.requestProductList()
}