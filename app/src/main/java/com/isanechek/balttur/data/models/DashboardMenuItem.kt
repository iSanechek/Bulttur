package com.isanechek.balttur.data.models

import com.isanechek.balttur._drawable

data class DashboardMenuItem(
    val data: String,
    val title: String,
    val color: String,
    val secondColor: String,
    val iconId: Int
) {

    companion object {
        fun items() = listOf(
            DashboardMenuItem(
                data = "https://balttur.spb.ru/tourists/news/",
                title = "Новости",
                color = "#7E57C2",
                secondColor = "#B39DDB",
                iconId = _drawable.news_white
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/countries/",
                title = "Страны",
                color = "#0091EA",
                secondColor = "#00B0FF",
                iconId = _drawable.eart_white
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/search/",
                title = "Поиск",
                color = "#FF6E40",
                secondColor = "#FF9E80",
                iconId = _drawable.ic_search_black_24dp
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/visas/",
                title = "Визы",
                color = "#FF9100",
                secondColor = "#FFD180",
                iconId = _drawable.visa_white
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/tourists/",
                title = "Туристам",
                color = "#66BB6A",
                secondColor = "#81C784",
                iconId = _drawable.alert_white
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/travel-agencies/",
                title = "Турагенствам",
                color = "#F57F17",
                secondColor = "#F9A825",
                iconId = _drawable.agencies_white
            ),
            DashboardMenuItem(
                data = "dev",
                title = "Разработчики",
                color = "#2E7D32",
                secondColor = "#58975B",
                iconId = _drawable.dev_white
            )
        )
    }
}