package com.isanechek.balttur.utils

import android.content.SharedPreferences
import androidx.core.content.edit

interface PrefUtils {
    var isWarningBrowserShow: Boolean
    var isLongShowWarning: Boolean
    var isLicenseShow: Boolean
}

class PrefUtilsImpl(private val pref: SharedPreferences) : PrefUtils {

    override var isLicenseShow: Boolean
        get() = pref.getBoolean("show_license", true)
        set(value) {
            pref.edit {
                putBoolean("show_license", value)
            }
        }

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