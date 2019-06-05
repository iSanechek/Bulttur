package com.isanechek.balttur.fragments.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.data.models.Resource
import com.isanechek.balttur.utils.Tracker
import kotlinx.coroutines.Dispatchers
import org.jsoup.Jsoup

class CountryViewModel(private val platform: PlatformContract,
                       private val client: NetworkClient,
                       private val tracker: Tracker) : ViewModel() {


    fun loadData(update: Boolean = false): LiveData<Resource<List<Pair<String, String>>>> = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        val cache = platform.getData("country_cache")
        when {
            cache.isNotEmpty() && update -> {
                val data = cache
                    .map { it.split(",") }
                    .map { Pair(it[0], it[1]) }
                emit(Resource.success(data))
            }
            else -> {
                emit(Resource.loading(null))
                try {
                    val response = client.request("https://balttur.spb.ru/countries/")
                    if (response != null) {
                        val data = mutableListOf<Pair<String, String>>()
                        val body = Jsoup.parse(response).body()
                        val children = body.selectFirst("div.wrapper.contentwrapper div.contentbg section.content-section div.container div.innerblock.textcontent.maxpadding div.countrysec").children()
                        for (child in children) {
                            val items = child.select("div.countrysec_title")
                            for (item in items) {
                                val a = item.selectFirst("a")
                                data.add(Pair(a.text(), a.attr("href")))
                            }
                        }
                        if (data.isNotEmpty()) {
                            platform.saveData("country_cache", data.map { "${it.first},${it.second}" }.toSet())
                            emit(Resource.loading(data))
                        }
                    }
                } catch (ex: Exception) {
                    tracker.event("CountryViewModel", "При загрузке стран что-то пошло не так!", ex)
                    emit(Resource.error("Что-то пошло не так! :(", null))
                }
            }
        }
    }
}