package com.isanechek.balttur.utils

import com.squareup.picasso.Callback

class KCallback : Callback {

    override fun onError(e: Exception?) {
        onError?.invoke()
    }

    private var onSuccess: (() -> Unit)? = null
    private var onError: (() -> Unit)? = null

    override fun onSuccess() {
        onSuccess?.invoke()
    }

    fun onSuccess(function: () -> Unit) {
        this.onSuccess = function
    }

    fun onError(function: () -> Unit) {
        this.onError = function
    }
}