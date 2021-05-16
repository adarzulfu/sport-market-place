package com.example.hilt.scene.productlist

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.hilt.R
import com.example.hilt.base.BaseFragment
import com.example.hilt.databinding.FragmentProductListBinding
import com.example.hilt.internal.ext.hideKeyboard
import com.example.hilt.scene.uimodel.ProductUIModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment :
    BaseFragment<FragmentProductListBinding, ProductListViewModel>(ProductListViewModel::class),
    ProductItemClickListener {

    private var adapter: ProductListAdapter = ProductListAdapter(this)
    private var searchMenuItem: MenuItem? = null
    private var profileMenuItem: MenuItem? = null

    override fun provideViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitionEnter()
    }

    override fun initialize() {
        super.initialize()
        postponeEnterTransition()
        setHasOptionsMenu(true)
        binding.recyclerViewProductList.adapter = adapter
        viewModel.loadProductList()
    }

    override fun observeData() {
        super.observeData()
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            adapter.updateProductList(it)
            listenOnUiDrawn()
        }
        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { show ->
                binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
    }

    private fun listenOnUiDrawn() {
        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setSharedElementTransitionEnter() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.change_image_transform)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_list, menu)

        searchMenuItem = menu.findItem(R.id.action_search)

        configureSearchMenuItem()
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onItemSelected(imageView: ImageView, uiModel: ProductUIModel) {
        imageView.hideKeyboard()
        navigateToDetail(imageView, uiModel)
    }

    private fun navigateToDetail(imageView: ImageView, uiModel: ProductUIModel) {

        val extras = FragmentNavigatorExtras(
            imageView to uiModel.id
        )
        findNavController().navigate(
            ProductListFragmentDirections
                .actionProductListFragmentToProductDetailFragment(uiModel.id, uiModel),
            extras
        )
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
}