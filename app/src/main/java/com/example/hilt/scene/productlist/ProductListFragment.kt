package com.example.hilt.scene.productlist

import androidx.navigation.fragment.findNavController
import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductListBinding

class ProductListFragment : BaseFragment<FragmentProductListBinding>() {

    override fun provideViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    override fun initialize() {
        super.initialize()
    }

    override fun observeData() {
        super.observeData()
        binding.buttonNext.setOnClickListener {
            val action =
                ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment()
            findNavController().navigate(action)
        }
    }
}