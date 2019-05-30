package com.isanechek.balttur.data.parsers

import com.isanechek.balttur.data.models.*
import com.isanechek.balttur.utils.Tracker
import com.isanechek.balttur.utils.createJO
import com.isanechek.balttur.utils.toJson
import com.isanechek.balttur.utils.toPair
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import kotlin.coroutines.resume

interface HomePageParser {
    suspend fun bodyParse(source: String): Element?
    suspend fun parseToursInfo(body: Element): Set<ToursInfo>
    suspend fun parseSection(body: Element): Elements?
    suspend fun parseNewsBlock(body: Element): Set<News>
    suspend fun parseHeaderMenu(body: Element): Set<HeaderMenu>
    suspend fun parseTextContent(body: Element): TextContentRoot
}


class HomePageParserImpl(private val tracker: Tracker) : HomePageParser {

    override suspend fun bodyParse(source: String): Element? = suspendCancellableCoroutine { c ->
        try {
            c.resume(Jsoup.parse(source))
        } catch (ex: Exception) {
            tracker.event(TAG, "bodyParse error!", ex)
            c.resume(null)
        }
    }

    override suspend fun parseToursInfo(body: Element): Set<ToursInfo> = suspendCancellableCoroutine { c ->
        try {
            val temp = mutableSetOf<ToursInfo>()
            val content =
                body.selectFirst("div.wrapper header div.mainheaderbgblock.posR div.mainheaderbg.posR div.container.posR div.mainrightblock.posR div.mainslider.posA div.slickslider")
            for (item in content.select("div.ms_item_block")) {
                val groupTitleElement = item.selectFirst("div.aut.ms_grouptitle")
                val (groupUrl, groupTitle) = getUrlAndTitle(groupTitleElement.selectFirst("a"))
                val (bigUrl, bigTitle) = getUrlAndTitle(item.selectFirst("div.ms_bigtitle").selectFirst("a"))
                val days = item.selectFirst("div.ms_fromtoday")
                val daysTitle = days.selectFirst("div.ms_fromtoday_title")
                val tempDates = mutableSetOf<String>()
                for (dates in days.select("div.ms_fromtoday_item")) {
                    tempDates.add(dates.text())
                }
                temp.add(
                    ToursInfo(
                        groupTitle = groupTitle,
                        groupUrl = groupUrl,
                        bigTitle = bigTitle,
                        bigUrl = bigUrl,
                        price = item.selectFirst("div.ms_price").text(),
                        description = item.selectFirst("div.ms_description").text(),
                        daysTitle = daysTitle.text(),
                        dates = tempDates.toSet()
                    )
                )
            }
            c.resume(temp)
        } catch (ex: Exception) {
            tracker.event(TAG, "parseToursInfo error!", ex)
            c.resume(emptySet())
        }
    }

    override suspend fun parseSection(body: Element): Elements? = suspendCancellableCoroutine { c ->
        try {
            c.resume(body.select("section"))
        } catch (ex: Exception) {
            tracker.event(TAG, "parseSection error!", ex)
            c.resume(null)
        }
    }

    override suspend fun parseNewsBlock(body: Element): Set<News> = suspendCancellableCoroutine { c ->
        try {
            val block = body.selectFirst("div.container div.textcontent.maincont div.mainnewsblock")
            val temp = mutableListOf<News>()
            for (item in block.select("div.mnb_item.floatL")) {
                val (titleUrl, title) = getUrlAndTitle(item.selectFirst("div.mnb_title").selectFirst("a"))
                val (link, linkDescription) = getUrlAndTitle(item.selectFirst("div.mnb_link").selectFirst("a"))
                temp.add(
                    News(
                        imgUrl = item.selectFirst("div.mnb_img img").attr("src"),
                        date = item.selectFirst("div.mnb_date").text(),
                        infoBlock = parseNewsInfoBlock(item.selectFirst("div.mnb_text")),
                        link = link,
                        linkDescription = linkDescription,
                        title = title,
                        titleUrl = titleUrl
                    )
                )
            }
            c.resume(temp.toSet())
        } catch (ex: Exception) {
            tracker.event(TAG, "parseNewsBlock error!", ex)
            c.resume(emptySet())
        }
    }

    override suspend fun parseHeaderMenu(body: Element): Set<HeaderMenu> = suspendCancellableCoroutine { c ->
        try {
            val content = body.selectFirst("div.container div.TopMenu.hidden-menu ul")
            val temp = mutableSetOf<HeaderMenu>()
            for (item in content.children()) {
                /*Countries*/
                val tempCountries = mutableListOf<Countries>()
                val menuCountriesMenuItem = item.selectFirst("div.sf-mega div.sf-mega-in.posR")
                if (menuCountriesMenuItem != null) {
                    for (ff in menuCountriesMenuItem.children()) {
                        val tempCountry = mutableListOf<Country>()
                        for (i in menuCountriesMenuItem.select("div.sf-mega-item")) {
                            val c = i.selectFirst("a")
                            val (url, title) = getUrlAndTitle(c)
                            val backUrl = c.attr("style")
                                .replace("background-image: url('", "")
                                .replace("');", "")
                            tempCountry.add(Country(name = title, coverUrl = backUrl, url = url))
                        }

                        val rootTitle = ff.selectFirst("div.sf-mega-tile")
                        if (rootTitle != null) {
                            tempCountries.add(Countries(region = rootTitle.text(), countries = tempCountry))
                        } else {
                            tempCountries.add(Countries(region = "", countries = tempCountry))
                        }
                    }
                }

                val tempMenu = mutableSetOf<ItemMenu>()
                val tItems = item.selectFirst("ul")
                if (tItems != null) {
                    for (tItem in tItems.children()) {
                        val (tUrl, tTitle) = getUrlAndTitle(tItem.selectFirst("a"))
                        tempMenu.add(ItemMenu(title = tTitle, url = tUrl))
                    }
                }

                val a = item.selectFirst("a")
                val (menuUrl, menuTitle) = getUrlAndTitle(a)
                temp.add(
                    HeaderMenu(
                        title = menuTitle,
                        url = menuUrl,
                        countries = tempCountries.toSet(),
                        subMenus = tempMenu.toSet()
                    )
                )
                if (menuTitle.contains("Турагентствам")) {
                    break
                }
            }
            c.resume(temp)
        } catch (ex: Exception) {
            tracker.event(TAG, "parseHeaderMenu error!", ex)
            c.resume(emptySet())
        }
    }

    override suspend fun parseTextContent(body: Element): TextContentRoot = suspendCancellableCoroutine { c ->
        try {
            val temp = mutableListOf<TextContent>()
            val content = body.selectFirst("div.container.exb_bg div.textcontent.maincont")
            for (item in content.children()) {
                when (item.tagName()) {
                    "h3", "h2", "h1", "p" -> temp.add(TextContent(content = item.text(), type = TextContent.STRING))
                    "div" -> {
                        val contentElement = item.selectFirst("div.ouradvantagesblock")
                        when {
                            contentElement != null -> temp.add(TextContent(content = item.text(), contents = parseTextContentHelper(contentElement), type = TextContent.LIST))
                            else -> temp.add(TextContent(content = item.text(), contents = parseTextContentHelper(item), type = TextContent.LIST))
                        }
                    }
                    "ul" -> {
                        val tempChild = item.children()
                            .map { TextContentChildData(it.text()) }
                            .toList()
                        temp.add(TextContent(content = item.text(), contents = TextContentChild(data = tempChild), type = TextContent.LIST))
                    }
                    else -> tracker.event(TAG, "parseTextContent error! Tag not find! $item")
                }
            }
            c.resume(TextContentRoot(items = temp))
        } catch (ex: Exception) {
            tracker.event(TAG, "parseTextContent error!", ex)
            c.resume(TextContentRoot(errorStatus = true, errorMessage = ex.message ?: "Что то пошло не так! :("))
        }
    }

    private fun parseTextContentHelper(element: Element?): TextContentChild = when {
        element != null -> {
            val temp = mutableListOf<TextContentChildData>()
            element.selectFirst("div.ouradvleft.floatL ul")
                .children()
                .mapTo(temp, { e ->  TextContentChildData(e.text()) })
                .toList()
            TextContentChild(data = temp)
        }
        else -> TextContentChild(errorStatus = true, errorMessage = "Не удалось распарсить данные!")
    }

    private fun parseNewsInfoBlock(element: Element): NewsInfoBlock {
        val tbody = element.selectFirst("table tbody")
        return when {
            tbody != null -> {
                val temp = mutableSetOf<String>()
                for (tr in tbody.select("tr")) {
                    for (td in tr.select("td p")) {
                        temp.add(td.text())
                    }
                }
                NewsInfoBlock(content = temp.joinToString(","), type = NewsInfoBlock.TYPE_LIST)
            }
            else -> NewsInfoBlock(content = element.text(), type = NewsInfoBlock.TYPE_STRING)
        }
    }



    private fun getUrlAndTitle(element: Element): Pair<String, String> = Pair(element.attr("href"), element.text())

    companion object {
        private const val TAG = "HomePageParser"

        const val JO_ERROR_STATUS_KEY = "is_error"
        const val JO_ERROR_MESSAGE_KEY = "error_message"
    }

}