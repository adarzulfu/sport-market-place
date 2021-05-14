package com.example.hilt.application

import android.app.Application
import com.example.hilt.internal.util.NetworkStateHolder.registerConnectivityMonitor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerConnectivityMonitor()
    }
}