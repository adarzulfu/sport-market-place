package com.example.hilt.scene.productdetail

import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductDetailBinding

class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(ProductDetailViewModel::class) {

    override fun provideViewBinding(): FragmentProductDetailBinding =
        FragmentProductDetailBinding.inflate(layoutInflater)

    override fun initialize() {
        super.initialize()
    }

}