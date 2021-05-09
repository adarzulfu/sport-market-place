package com.example.hilt.data.domain

import com.example.hilt.data.remote.model.ProductItemResponse
import com.example.hilt.data.remote.repository.ProductRepository
import com.example.hilt.internal.util.UseCase
import com.example.hilt.scene.productlist.ProductUIModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductListUseCase @Inject constructor(private val productRepository: ProductRepository) :
    UseCase<List<ProductUIModel>, UseCase.None>() {

    override suspend fun buildUseCase(params: None): List<ProductUIModel> =
        convertToUIModel(productRepository.getProductList())

    // TODO FIX ME FOR DIFFERENT CURRENCY TYPES
    private fun convertToUIModel(productList: List<ProductItemResponse?>): List<ProductUIModel> {
        return productList.mapNotNull { item ->
            if (item != null) {
                ProductUIModel(
                    groupName = "Sample Group Name",
                    productName = item.name ?: "",
                    description = item.description ?: "",
                    price = item.currency ?: "" + item.price
                )
            } else {
                null
            }
        }
    }
}