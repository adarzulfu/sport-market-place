package com.example.hilt.scene.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hilt.base.BaseViewModel
import com.example.hilt.data.domain.GetProductListUseCase
import com.example.hilt.internal.util.Event
import com.example.hilt.internal.util.UseCase
import com.example.hilt.scene.uimodel.ProductUIModel
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

    private val _progressLiveData = MutableLiveData<Event<Boolean>>()
    val progressLiveData: LiveData<Event<Boolean>>
        get() = _progressLiveData

    private var initialProductList: List<ProductUIModel>? = null

    fun loadProductList() = viewModelScope.launch {
        showProgress()
        getProductListUseCase.run(UseCase.None).either(::handleFailure, ::handleProductList)
        hideProgress()
    }

    fun onQueryTextChange(queryText: String) {
        _productListLiveData.value = if (queryText.isEmpty()) {
            initialProductList
        } else {
            filterProductList(queryText)
        }
    }

    private fun handleProductList(productLis: List<ProductUIModel>) {
        _productListLiveData.value = productLis
        initialProductList = productLis
    }

    private fun showProgress() {
        _progressLiveData.value = Event(true)
    }

    private fun hideProgress() {
        _progressLiveData.value = Event(false)
    }

    private fun filterProductList(queryText: String): List<ProductUIModel>? {
        return initialProductList?.filter {
            it.productName.contains(queryText)
                    || it.description.contains(queryText)
                    || it.price.contains(queryText)
        }
    }

    override fun retryRequested() {
        loadProductList()
    }
}