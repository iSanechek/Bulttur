package com.isanechek.balttur.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting ?: false
    }
}