package com.example.hilt.scene.productdetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionSet
import androidx.core.transition.addListener
import androidx.navigation.fragment.navArgs
import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductDetailBinding
import com.example.hilt.scene.productdetail.rating.RatingDialogManager
import com.example.hilt.scene.productdetail.rating.RatingListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(ProductDetailViewModel::class),
    RatingListener {

    private val reviewAdapter = ReviewAdapter()

    private val args: ProductDetailFragmentArgs by navArgs()

    override fun provideViewBinding(): FragmentProductDetailBinding =
        FragmentProductDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val transitionSet = sharedElementEnterTransition as TransitionSet?
        transitionSet?.addListener(onEnd = {
            onUIReady()
        })
    }

    override fun initialize() {
        super.initialize()
        with(binding) {
            recyclerViewDetail.adapter = reviewAdapter
            textViewProductDescription.text = args.productUIModel?.description
            textViewProductName.text = args.productUIModel?.productName
            textViewProductPrice.text = args.productUIModel?.price
            imageViewPreview.transitionName = args.transitionId
            buttonAddReview.setOnClickListener { showRatingDialog() }
        }
    }

    private fun onUIReady() {
        args.productUIModel?.id?.let {
            viewModel.saveProductId(it)
            viewModel.loadReviews(it)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.reviewListLiveData.observe(viewLifecycleOwner) {
            reviewAdapter.updateProductList(it)
        }
    }

    override fun onRatingReady(rating: Int, comment: String) {
        viewModel.saveReview(rating, comment)
    }

    private fun showRatingDialog() {
        val ratingDialogManager =
            RatingDialogManager(requireContext(), this)
        ratingDialogManager.show()
    }
}