package com.isanechek.balttur.data.models

data class DashboardMenuItem (
    val id: String,
    val title: String,
    val color: String,
    val secondColor: String,
    val iconId: Int
) {
    companion object {
        const val COUNTRY_ID = "find_tour_id"
        const val TOURIST_ID = "tourist_id"
        const val TOUR_AG_ID = "tour_ag_id"
        const val VISA_ID = "visa_id"

    }
}