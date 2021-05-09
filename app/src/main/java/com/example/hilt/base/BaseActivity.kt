package com.example.hilt.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding: ViewBinding
    abstract fun provideViewBinding(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = provideViewBinding()
        super.onCreate(savedInstanceState)
    }


}