package com.example.hilt.data.remote.datasource

import com.example.hilt.data.remote.ProductService
import com.example.hilt.internal.util.BaseRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSource @Inject constructor(private val service: ProductService) :
    BaseRemoteDataSource() {

    suspend fun requestProductList() = invoke {
        service.requestGetProductList()
    }

    suspend fun requestSingleProduct(id: String) = invoke {
        service.requestGetSingleProduct(id)
    }
}