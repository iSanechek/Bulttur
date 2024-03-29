package com.isanechek.balttur.fragments.dashboard

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.isanechek.balttur.BASE_URL
import com.isanechek.balttur.DIGNITY_DATA
import com.isanechek.balttur._text
import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.data.database.dao.NewsDao
import com.isanechek.balttur.data.database.dao.ToursDao
import com.isanechek.balttur.data.database.entity.NewsEntity
import com.isanechek.balttur.data.database.entity.ToursInfoEntity
import com.isanechek.balttur.data.models.News
import com.isanechek.balttur.data.models.ToursInfo
import com.isanechek.balttur.data.parsers.HomePageParser
import com.isanechek.balttur.utils.NetworkUtils
import com.isanechek.balttur.utils.RequestLimiter
import com.isanechek.balttur.utils.Tracker
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class DashboardViewModel(
    application: Application,
    private val client: NetworkClient,
    private val parser: HomePageParser,
    private val platform: PlatformContract,
    private val newsDao: NewsDao,
    private val toursDao: ToursDao,
    private val tracker: Tracker
) : AndroidViewModel(application) {

    private val context: Context = getApplication()
    private val isTimeForUpdate = RequestLimiter(1, TimeUnit.DAYS, platform)
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _progressState: MutableLiveData<Boolean> = MutableLiveData()

    private val toNewsEntity: (News) -> NewsEntity = { n ->
        NewsEntity(
            id = n.title.hashCode(),
            content = n.infoBlock.content,
            linkDescription = n.linkDescription,
            link = n.link,
            date = n.date,
            contentType = n.infoBlock.type,
            imgUrl = n.imgUrl,
            title = n.title,
            titleUrl = n.titleUrl
        )
    }

    private val toToursInfoEntity: (ToursInfo) -> ToursInfoEntity = { t ->
        ToursInfoEntity(
            id = t.bigTitle.hashCode(),
            dates = t.dates.joinToString(","),
            daysTitle = t.daysTitle,
            description = t.description,
            price = t.price,
            bigUrl = t.bigUrl,
            bigTitle = t.bigTitle,
            groupUrl = t.groupUrl,
            groupTitle = t.groupTitle
        )
    }

    val history: MutableList<String> = mutableListOf()

    val errorMessage:LiveData<String>
        get() = _errorMessage

    val progressState: LiveData<Boolean>
        get() = _progressState

    val newsData: LiveData<List<NewsEntity>>
        get() = newsDao.load()

    val toursData: LiveData<List<ToursInfoEntity>>
        get() = toursDao.load()

    fun load(update: Boolean = false) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            when {
                update -> loadFromNetwork(true)
                newsDao.count() == 0 -> loadFromNetwork(false)
                isTimeForUpdate.shouldFetch(TAG) -> loadFromNetwork(true)
                else -> Unit
            }
        }
    }

    fun loadInfoData(): LiveData<String> = liveData(context = viewModelScope.coroutineContext + Dispatchers.Default) {
        do {
            val data = DIGNITY_DATA.split(".")
            val number = (data.indices).random()
            delay(7*1000)
            emit(data[number])
            if (history.size >= 10) history.removeAt(0)
            history.add(data[number])
        } while (true)
    }

    fun showNetworkErrorMessage() {
        hideProgressAndErrorMsg(context.getString(_text.notwork_error_msg))
    }

    private suspend fun loadFromNetwork(update: Boolean) {
        _progressState.postValue(true)
        try {
            val source = client.request(BASE_URL)
            if (source != null) {
                val body = parser.bodyParse(source)
                if (body != null) {
                    val newsData = parser.parseNewsBlock(body).map { toNewsEntity(it) }.toList()
                    val toursInfoData = parser.parseToursInfo(body).map { toToursInfoEntity(it) }.toList()
                    when {
                        update -> {
                            newsDao.update(newsData)
                            toursDao.update(toursInfoData)
                        }
                        else -> {
                            newsDao.insert(newsData)
                            toursDao.insert(toursInfoData)

                        }
                    }
                    _progressState.postValue(false)
                } else {
                    tracker.event(TAG, "Parse data fail! :(")
                    hideProgressAndErrorMsg("Упс... При парсинге произошла ошибкаю :(")
                }
            } else {
                tracker.event(TAG, "Load data fail! :(")
                hideProgressAndErrorMsg("Упс... При загрузке произошла ошибкаю :(")
            }
        } catch (ex: Exception) {
            tracker.event(TAG, "Load data fail! :(", ex)
            hideProgressAndErrorMsg("Упс... При загрузке произошла ошибкаю :(")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }

    private fun hideProgressAndErrorMsg(msg: String) {
        _progressState.postValue(false)
        _errorMessage.postValue(msg)
    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }

}