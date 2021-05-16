package com.example.hilt.scene.productdetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionSet
import androidx.core.transition.addListener
import androidx.navigation.fragment.navArgs
import com.example.hilt.R
import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductDetailBinding
import com.example.hilt.internal.ext.loadImageForSharedAnimation
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
        setSharedElementTransitionEnter()
    }

    override fun initialize() {
        super.initialize()
        postponeEnterTransition()
        setUiForTransitionAnimations()

        with(binding) {
            recyclerViewDetail.adapter = reviewAdapter
            textViewProductDescription.text = args.productUIModel.description
            textViewProductName.text = args.productUIModel.productName
            textViewProductPrice.text = args.productUIModel.price
            buttonAddReview.setOnClickListener { showRatingDialog() }
        }
    }

    private fun setUiForTransitionAnimations() {
        binding.imageViewPreview1.transitionName = args.productUIModel.id
        binding.imageViewPreview1.loadImageForSharedAnimation(args.productUIModel.imageUrl) {
            startPostponedEnterTransition()
        }
    }

    private fun setSharedElementTransitionEnter() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.change_image_transform)
        val transitionSet = sharedElementEnterTransition as TransitionSet?
        transitionSet?.addListener(onEnd = {
            onTransitionAnimationCompleted()
        })
    }

    private fun onTransitionAnimationCompleted() {
        args.productUIModel.id.let {
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