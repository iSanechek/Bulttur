package com.isanechek.balttur.utils

import android.content.SharedPreferences
import androidx.core.content.edit

interface PrefUtils {
    var isWarningBrowserShow: Boolean
    var isLongShowWarning: Boolean
}

class PrefUtilsImpl(private val pref: SharedPreferences) : PrefUtils {
    override var isLongShowWarning: Boolean
        get() = pref.getBoolean("long_dialog", true)
        set(value) {
            pref.edit {
                putBoolean("long_dialog", value)
            }
        }

    override var isWarningBrowserShow: Boolean
        get() = pref.getBoolean("is_warning_browser_show", true)
        set(value) {
            pref.edit {
                putBoolean("is_warning_browser_show", value)
            }
        }


}