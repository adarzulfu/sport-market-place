package com.example.hilt.scene.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hilt.base.BaseViewModel
import com.example.hilt.data.domain.GetProductListUseCase
import com.example.hilt.internal.util.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase
) : BaseViewModel() {

    private val _productListLiveData = MutableLiveData<List<ProductUIModel>>()
    val productListLiveData: LiveData<List<ProductUIModel>>
        get() = _productListLiveData

    fun loadProductList() = viewModelScope.launch {
        getProductListUseCase.run(UseCase.None).either(::handleFailure, ::handleProductList)
    }

    private fun handleProductList(productLis: List<ProductUIModel>) {
        _productListLiveData.value = productLis
    }
}