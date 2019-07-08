package com.isanechek.balttur.fragments.web

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class TurClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }
}