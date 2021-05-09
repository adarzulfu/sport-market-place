package com.example.hilt.data.remote

import com.example.hilt.data.remote.model.ProductItemResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET(PRODUCT)
    suspend fun getProductList(): List<ProductItemResponse?>

    @GET(SINGLE_PRODUCT)
    suspend fun getSingleProduct(@Path("id") id: String): ProductItemResponse

    companion object {
        const val PRODUCT = "/product"
        const val SINGLE_PRODUCT = "/product/{id}"
    }
}