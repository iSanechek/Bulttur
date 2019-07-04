package com.isanechek.balttur.data.models

import com.isanechek.balttur._drawable

data class CompanyItem(val id: Int, val icon: Int, val title: String, val data: String, val color: String) {

    companion object {
        val items = listOf(
            CompanyItem(
                id = 0,
                icon = _drawable.phone_white,
                title = "Позвонить",
                data = "",
                color = "#1DE9B6"
            ),
            CompanyItem(
                id = 1,
                icon = _drawable.clock_white,
                title = "Режим работы",
                data = "",
                color = "#FFFF00"
            ),
            CompanyItem(
                id = 2,
                icon = _drawable.home_map,
                title = "На карте",
                data = "",
                color = "#81C784"
            )
        )
    }
}