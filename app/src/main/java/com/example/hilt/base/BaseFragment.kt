package com.example.hilt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import com.example.hilt.R
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
        initialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val message = when (failure) {
            is Failure.NoConnectivityError -> getString(R.string.common_error_network_connection)
            is Failure.ApiError -> failure.message
            is Failure.UnknownError ->
                failure.exception.localizedMessage
                    ?: getString(R.string.common_error_unknown)
            is Failure.TimeOutError -> getString(R.string.common_error_timeout)
            else -> failure.message ?: failure.toString()
        }
        context?.showPopup(
            PopupModel(
                message = message
            )
        )
    }
}