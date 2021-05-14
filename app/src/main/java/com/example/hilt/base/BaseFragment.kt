package com.example.hilt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import com.example.hilt.R
import com.example.hilt.databinding.ItemInternetConnectionBinding
import com.example.hilt.internal.ext.observeNonNull
import com.example.hilt.internal.ext.showPopup
import com.example.hilt.internal.popup.PopupModel
import com.example.hilt.internal.util.Failure
import kotlin.reflect.KClass

abstract class BaseFragment<T : ViewBinding, VM : BaseViewModel>(viewModelClass: KClass<VM>) :
    Fragment() {

    lateinit var binding: T
    abstract fun provideViewBinding(): T

    val viewModel: VM by createViewModelLazy(
        viewModelClass, { this.viewModelStore }
    )

    lateinit var internetConnectionBinding: ItemInternetConnectionBinding

    open fun initialize() {
        // Do nothing in here. Child classes should implement when necessary
    }

    open fun observeData() {
        // Do nothing in here. Child classes should implement when necessary
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = provideViewBinding()
        internetConnectionBinding = createNoConnectionUI()
        initialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFailure()
        observeData()
    }


    private fun observeFailure() {
        viewModel.failure.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { failure ->
                handleFailure(failure)
            }
        }
    }


    protected open fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NoConnectivityError -> showInternetConnectionUi()
            is Failure.ApiError -> showErrorPopUp(failure.message)
            is Failure.UnknownError -> showErrorPopUp(
                failure.exception.localizedMessage
                    ?: getString(R.string.common_error_unknown)
            )
            is Failure.TimeOutError -> showErrorPopUp(getString(R.string.common_error_timeout))
            else -> showErrorPopUp(failure.message ?: failure.toString())
        }
    }

    private fun showErrorPopUp(message: String) {
        context?.showPopup(
            PopupModel(
                message = message
            )
        )
    }

    private fun showInternetConnectionUi() {
        (binding.root as ViewGroup).addView(internetConnectionBinding.root)
    }

    private fun createNoConnectionUI(): ItemInternetConnectionBinding {
        val noConnectionBinding =
            ItemInternetConnectionBinding.inflate(layoutInflater, binding.root as ViewGroup, false)

        noConnectionBinding.buttonNoInternetConnection.setOnClickListener {
            (binding.root as ViewGroup).removeView(internetConnectionBinding.root)
            viewModel.retryRequested()
        }
        return noConnectionBinding
    }

}