package com.isanechek.balttur.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.io.File

interface PlatformContract {
    fun getPathCacheFolder(): String
    val isLicenseShow: Boolean
    fun licenseMarkNotShow()
}

class PlatformContractImpl(private val context: Context, private val pref: SharedPreferences) : PlatformContract {

    override fun licenseMarkNotShow() {
        pref.edit {
            putBoolean("is_license", false)
        }
    }

    override val isLicenseShow: Boolean
        get() = pref.getBoolean("is_license", true)

    override fun getPathCacheFolder(): String {
        val path = context.filesDir.absolutePath + File.separator + "cache"
        val dir = File(path)
        when {
            !dir.exists() -> dir.mkdirs()
        }
        val nomedia = File(path + File.separator + ".nomedia")
        when {
            !nomedia.exists() -> nomedia.mkdirs()
        }

        return dir.absolutePath
    }

}