package com.example.hilt.data.remote

import com.example.hilt.data.remote.model.ProductItemResponse
import retrofit2.http.GET

interface ProductService {

    @GET(PRODUCT)
    suspend fun requestGetProductList(): List<ProductItemResponse?>

    companion object {
        const val PRODUCT = "/product"
    }
}