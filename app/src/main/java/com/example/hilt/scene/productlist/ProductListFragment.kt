package com.example.hilt.scene.productlist

import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment :
    BaseFragment<FragmentProductListBinding, ProductListViewModel>(ProductListViewModel::class) {

    private var adapter: ProductListAdapter = ProductListAdapter()

    override fun provideViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    override fun initialize() {
        super.initialize()
        binding.recyclerViewProductList.adapter = adapter

        viewModel.loadProductList()
    }

    override fun observeData() {
        super.observeData()
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            adapter.updateProductList(it)
        }
    }
}