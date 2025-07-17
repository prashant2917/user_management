package com.pocket.usermanagement.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class NetworkLiveData(private val connectivityManager: ConnectivityManager) : LiveData<Boolean>() {
    val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {

            postValue(true)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }


    override fun onActive() {
        val netWorkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(netWorkRequest, connectivityCallback)
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(connectivityCallback)
    }
}