package com.example.hilt.scene.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hilt.R
import com.example.hilt.base.BaseViewModel
import com.example.hilt.data.domain.GetReviewListUseCase
import com.example.hilt.data.domain.SaveReviewUseCase
import com.example.hilt.scene.uimodel.BaseReviewUIModel
import com.example.hilt.scene.uimodel.NoReviewUIModel
import com.example.hilt.scene.uimodel.ReviewUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getReviewListUseCase: GetReviewListUseCase,
    private val saveReviewUseCase: SaveReviewUseCase
) : BaseViewModel() {


    private val _reviewListLiveData = MutableLiveData<List<BaseReviewUIModel>>()
    val reviewListLiveData: LiveData<List<BaseReviewUIModel>>
        get() = _reviewListLiveData

    private var productId: String = ""


    fun saveProductId(productId: String) {
        this.productId = productId
    }

    fun loadReviews(productId: String) = viewModelScope.launch {
        getReviewListUseCase.run(GetReviewListUseCase.Params(productId))
            .either(::handleFailure, ::handleReviewList)
    }

    fun saveReview(rating: Int, comment: String) = viewModelScope.launch {
        saveReviewUseCase.run(SaveReviewUseCase.Params(productId, rating, "", comment))
            .either(::handleFailure, ::handleReviewSaved)
    }

    private fun handleReviewSaved() {
        loadReviews(productId)
    }

    private fun handleReviewList(reviewUiList: List<ReviewUIModel>) {
        if (reviewUiList.isNotEmpty()) {
            _reviewListLiveData.value = reviewUiList
        } else {
            _reviewListLiveData.value = listOf(NoReviewUIModel(R.string.product_detail_no_review))
        }
    }

    override fun retryRequested() {
        loadReviews(productId)
    }
}