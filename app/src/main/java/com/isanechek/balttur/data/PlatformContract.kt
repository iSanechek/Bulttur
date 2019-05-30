package com.isanechek.balttur.data

import android.content.Context
import java.io.File

interface PlatformContract {

    fun getPathCacheFolder(): String
}

class PlatformContractImpl(private val context: Context) : PlatformContract {

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