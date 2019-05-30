package com.isanechek.balttur.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.epam.coroutinecache.api.CacheParams
import com.epam.coroutinecache.api.CoroutinesCache
import com.epam.coroutinecache.mappers.GsonMapper
import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.models.HomeResult
import com.isanechek.balttur.data.parsers.HomePageParser
import com.isanechek.balttur.data.providers.HomePageCacheProvider
import com.isanechek.balttur.utils.Tracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File

interface HomeRepository {
    suspend fun loadData()
}


class HomeRepositoryImpl(
    private val client: NetworkClient,
    private val parser: HomePageParser,
    private val tracker: Tracker,
    private val context: Context
) : HomeRepository {

//    private val coroutinesCache: CoroutinesCache = CoroutinesCache(CacheParams(10, GsonMapper(), getPath()))
//    private val cacheProviders: HomePageCacheProvider = coroutinesCache.using(HomePageCacheProvider::class.java)

    init {
        Log.e("HYI", "Repository")
    }

    override suspend fun loadData() {

        Log.e("HYI", "Repository2")

        val path = getPath()
        Log.e("PAT", path.absolutePath)
        tracker.event("PATH", path.absolutePath)

//        val i = cacheProviders.getTextContent {
//            val response = client.request("")!!
//            val body = Jsoup.parse(response)
//            val sections = parser.parseSection(body)!!
//            parser.parseTextContent(sections[1])
//        }
//        tracker.event("Repository", "Data $i")

    }

    private fun getPath(): File {
        val path = context.filesDir.absolutePath + File.separator + "cache"
        val dir = File(path)
        when {
            !dir.exists() -> dir.mkdirs()
        }
        val nomedia = File(path + File.separator + ".nomedia")
        when {
            !nomedia.exists() -> nomedia.mkdirs()
        }

        return dir
    }

    private suspend fun loadDataFromNetwork() {
        val response = client.request("")
        if (response != null) {
            val body = Jsoup.parse(response)
            val sections = parser.parseSection(body)
            if (sections != null) {
                val c = parser.parseTextContent(sections[1])
            }
        }

    }

    private suspend fun parseBody(body: Element) = coroutineScope {
        val toursInfoResult = async { parser.parseToursInfo(body) }
        val sections = withContext(Dispatchers.Default) { parser.parseSection(body) }
        if (sections != null) {
            val newsBlockResult = async { parser.parseNewsBlock(sections[2]) }
            val headerMenuResult = async { parser.parseHeaderMenu(sections[0]) }
            val contentResult = async { parser.parseTextContent(sections[1]) }
        }

    }
}
