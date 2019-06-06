package com.isanechek.balttur.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.io.File

interface PlatformContract {
    fun getPathCacheFolder(): String
    val isLicenseShow: Boolean
    fun licenseMarkNotShow()
    fun getData(key: String): Set<String>
    fun saveData(key: String, data: Set<String>)
    fun getLastTimeUpdate(key: String): Long
    fun setLastTimeUpdate(key: String, time: Long)
}

class PlatformContractImpl(private val context: Context, private val pref: SharedPreferences) : PlatformContract {

    override fun getLastTimeUpdate(key: String): Long = pref.getLong(key, 0L)

    override fun setLastTimeUpdate(key: String, time: Long) {
        pref.edit {
            putLong(key, time)
        }
    }


    override fun getData(key: String): Set<String> = pref.getStringSet(key, emptySet()) ?: emptySet()

    override fun saveData(key: String, data: Set<String>) {
        pref.edit {
            putStringSet(key, data)
        }
    }

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