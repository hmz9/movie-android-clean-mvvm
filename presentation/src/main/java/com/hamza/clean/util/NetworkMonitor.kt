package com.hamza.clean.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hamza.clean.util.NetworkMonitor.NetworkState.Available
import com.hamza.clean.util.NetworkMonitor.NetworkState.Lost
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * @author by Ameer Hamza on 03/09/2023
 */
class NetworkMonitor(
    @ApplicationContext context: Context
) {

    enum class NetworkState {
        Available, Lost;

        fun isAvailable() = this == Available
    }

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.N)
    val networkState: Flow<NetworkState> = callbackFlow {

        launch { send(getInitialState()) }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(Available) }
                Log.d("XXX", "NetworkMonitor: onAvailable() called")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(Lost) }
                Log.d("XXX", "NetworkMonitor: onLost() called")
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            Log.d("XXX", "NetworkMonitor: awaitClose")
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getInitialState(): NetworkState = if (connectivityManager.activeNetwork != null) Available else Lost
}