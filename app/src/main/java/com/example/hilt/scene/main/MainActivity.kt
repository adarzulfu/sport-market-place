package com.example.hilt.scene.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.hilt.R
import com.example.hilt.base.BaseActivity
import com.example.hilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun provideViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}