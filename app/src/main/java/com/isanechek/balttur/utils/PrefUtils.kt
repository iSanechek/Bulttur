package com.isanechek.balttur.utils

import android.content.SharedPreferences
import androidx.core.content.edit

interface PrefUtils {
    var isWarningBrowserShow: Boolean
}

class PrefUtilsImpl(private val pref: SharedPreferences) : PrefUtils {

    override var isWarningBrowserShow: Boolean
        get() = pref.getBoolean("is_warning_browser_show", true)
        set(value) {
            pref.edit {
                putBoolean("is_warning_browser_show", value)
            }
        }


}