package com.isanechek.balttur.utils

import android.util.Log
import com.isanechek.balttur.BuildConfig
import com.yandex.metrica.YandexMetrica

interface Tracker {
    fun event(tag: String, msg: String, exception: Throwable?)
    fun event(tag: String, msg: String)
}

class TrackerImpl : Tracker {

    override fun event(tag: String, msg: String) {
        event(tag, msg, null)
    }

    override fun event(tag: String, msg: String, exception: Throwable?) {
        if (BuildConfig.DEBUG) {
            when {
                exception != null -> Log.e("Balttur", "$tag -> $msg", exception)
                else -> Log.e("Balttur", "$tag -> $msg")
            }
        } else {
            when {
                exception != null -> YandexMetrica.reportError(msg, exception)
                else -> YandexMetrica.reportEvent(tag, mapOf(Pair(msg, "no parameters")))
            }
        }
    }
}