package com.example.hilt.scene.main

import androidx.lifecycle.ViewModel
import com.example.hilt.data.remote.ProductService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val productService: ProductService
) : ViewModel() {

    fun test() {

    }
}