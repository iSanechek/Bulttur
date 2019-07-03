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
                data = "https://balttur.spb.ru/countries/",
                title = "Страны",
                color = "#0091EA",
                secondColor = "#00B0FF",
                iconId = _drawable.ic_place_black_24dp
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/visas/",
                title = "Визы",
                color = "#FF9100",
                secondColor = "#FFD180",
                iconId = _drawable.ic_place_black_24dp
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/tourists/",
                title = "Туристам",
                color = "#F57F17",
                secondColor = "#F9A825",
                iconId = _drawable.ic_people_white_24dp
            ),
            DashboardMenuItem(
                data = "https://balttur.spb.ru/travel-agencies/",
                title = "Турагенствам",
                color = "#66BB6A",
                secondColor = "#81C784",
                iconId = _drawable.ic_place_black_24dp
            )
        )
    }
}