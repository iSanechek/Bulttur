package com.isanechek.balttur.data

import kotlinx.coroutines.suspendCancellableCoroutine
import org.jsoup.nodes.Element
import java.text.ParseException

class HomePageParser : PageParser<String> {

    override suspend fun parse(source: String): String = suspendCancellableCoroutine { c ->

        try {

        } catch (ex: ParseException) {

        }
    }

    private fun parseHeaderMenu(element: Element) {
        val content = element.selectFirst("div.container div.TopMenu.hidden-menu ul")
        for (item in content.children()) {
            val a = item.selectFirst("a")
            val (menuUrl, menuTitle) = getUrlAndTitle(a)

            println("menu url $menuUrl")
            println("menu title $menuTitle")


            /*Countries*/
            val menuCountriesMenuItem = item.selectFirst("div.sf-mega div.sf-mega-in.posR")
            if (menuCountriesMenuItem != null) {

                for (ff in menuCountriesMenuItem.children()) {
                    val rootTitle = ff.selectFirst("div.sf-mega-tile")
                    if (rootTitle != null) {
                        println(">>>>Root title<<<<< ${rootTitle.text()}")
                    }

                    for (i in menuCountriesMenuItem.select("div.sf-mega-item")) {
                        val c = i.selectFirst("a")
                        val (url, title) = getUrlAndTitle(c)
                        val backUrl = c.attr("style").replace("background-image: url('", "").replace("');", "")
                        println("url $url")
                        println("title $title")
                        println("background url $backUrl")
                    }
                }
            }


            val tItems = item.selectFirst("ul")
            if (tItems != null) {
                for (tItem in tItems.children()) {
                    val (tUrl, tTitle) = getUrlAndTitle(tItem.selectFirst("a"))
                    println("Title $tTitle")
                    println("Url $tUrl")
                }
            }

            if (menuTitle.contains("Турагентствам")) {
                break
            }
        }

    }

    private fun getUrlAndTitle(element: Element): Pair<String, String> = Pair(element.attr("href"), element.text())

}