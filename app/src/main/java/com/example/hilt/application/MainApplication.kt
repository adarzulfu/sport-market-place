package com.example.hilt.application

import android.app.Application
import com.example.hilt.data.remote.ProductService
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit

@HiltAndroidApp
class MainApplication : Application() {

}