package com.isanechek.balttur.utils

import android.os.SystemClock
import com.isanechek.balttur.data.PlatformContract
import java.util.concurrent.TimeUnit

class RequestLimiter(timeout: Int, timeUnit: TimeUnit, private val platform: PlatformContract) {
    private val timeout = timeUnit.toMillis(timeout.toLong())

    fun shouldFetch(key: String): Boolean {
        val lastFetched = platform.getLastTimeUpdate(key)
        val now = now()
        if (lastFetched == 0L) {
            platform.setLastTimeUpdate(key, now)
            return true
        }
        if (now - lastFetched > timeout) {
            platform.setLastTimeUpdate(key, now)
            return true
        }
        return false
    }

    fun reset(key: String) {
        platform.setLastTimeUpdate(key, 0L)
    }

    private fun now() = SystemClock.uptimeMillis()
}