package com.example.hilt.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding: T
    abstract fun provideViewBinding(): T

    open fun initialize(savedInstanceState: Bundle?) {
        // Do nothing in here. Child classes should implement when necessary
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = provideViewBinding()
        setContentView(binding.root)
        initialize(savedInstanceState)
    }
}