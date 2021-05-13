package com.example.hilt.scene.productdetail

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.navigation.fragment.navArgs
import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductDetailBinding

class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(ProductDetailViewModel::class) {

    private val args: ProductDetailFragmentArgs by navArgs()

    override fun provideViewBinding(): FragmentProductDetailBinding =
        FragmentProductDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun initialize() {
        super.initialize()
        with(binding) {
            textViewProductDescription.text = args.productUIModel?.description
            textViewProductName.text = args.productUIModel?.productName
            textViewProductPrice.text = args.productUIModel?.price
            imageViewPreview.transitionName = args.transitionId
        }
    }
}