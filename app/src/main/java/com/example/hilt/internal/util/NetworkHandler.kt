package com.example.hilt.internal.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

interface NetworkState {
    val isConnected: Boolean
    val network: Network?
}

internal class NetworkStateImp : NetworkState {
    override var network: Network? = null

    override var isConnected: Boolean = false
        set(value) {
            field = value
            NetworkEvents.notify(
                if (value) NetworkEvent.ConnectivityAvailable else NetworkEvent.ConnectivityLost
            )
        }
}

sealed class NetworkEvent {
    object ConnectivityLost : NetworkEvent()
    object ConnectivityAvailable : NetworkEvent()
}

object NetworkStateHolder : NetworkState {

    private lateinit var holder: NetworkStateImp

    override val isConnected: Boolean
        get() = holder.isConnected
    override val network: Network?
        get() = holder.network

    fun Application.registerConnectivityMonitor() {
        holder = NetworkStateImp()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            NetworkCallbackImp(holder)
        )
    }
}

internal class NetworkCallbackImp(private val holder: NetworkStateImp) :
    ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        holder.network = network
        holder.isConnected = true
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        // no opt
    }

    override fun onLost(network: Network) {
        holder.network = network
        holder.isConnected = false
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        // no opt
    }
}

object NetworkEvents : LiveData<NetworkEvent>() {
    internal fun notify(event: NetworkEvent) {
        postValue(event)
    }
}
