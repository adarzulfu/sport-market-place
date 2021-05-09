package com.example.hilt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    lateinit var binding: T
    abstract fun provideViewBinding(): T

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
}