package com.isanechek.balttur.data.repositories

import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.parsers.HomePageParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Element

interface HomeRepository {
    suspend fun loadData()
}


class HomeRepositoryImpl(private val client: NetworkClient,
                         private val parser: HomePageParser) : HomeRepository {

    override suspend fun loadData() {

    }

    private suspend fun loadDataFromNetwork() = withContext(Dispatchers.IO) {
        val response = client.request("")
        if (response != null) {
            val body = parser.bodyParse(response)
            if (body != null) {
                parseBody(body)
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
