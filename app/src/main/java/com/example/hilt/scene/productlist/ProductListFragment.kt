package com.example.hilt.scene.productlist

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.example.hilt.R
import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment :
    BaseFragment<FragmentProductListBinding, ProductListViewModel>(ProductListViewModel::class) {

    private var adapter: ProductListAdapter = ProductListAdapter()
    private var searchMenuItem: MenuItem? = null
    private var profileMenuItem: MenuItem? = null

    override fun provideViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    override fun initialize() {
        super.initialize()
        setHasOptionsMenu(true)
        binding.recyclerViewProductList.adapter = adapter
        viewModel.loadProductList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_list, menu)

        searchMenuItem = menu.findItem(R.id.action_search)

        configureSearchMenuItem()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun configureSearchMenuItem() {
        searchMenuItem?.apply {
            setOnActionExpandListener(menuActionExpandListener)
            (actionView as SearchView).apply {
                queryHint = ""
                setQueryChangeListener(this)
            }
        }
    }

    private val menuActionExpandListener = object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
            profileMenuItem?.isVisible = true
            return true
        }

        override fun onMenuItemActionExpand(item: MenuItem): Boolean {
            profileMenuItem?.isVisible = false
            return true
        }
    }

    private fun setQueryChangeListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                queryText?.let {
                    viewModel.onQueryTextChange(queryText)
                }
                return false
            }
        })
    }

    override fun observeData() {
        super.observeData()
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            adapter.updateProductList(it)
        }
    }
}