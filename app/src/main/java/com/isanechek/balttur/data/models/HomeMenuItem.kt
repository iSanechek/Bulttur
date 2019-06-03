package com.isanechek.balttur.data.models

data class HomeMenuItem(val id: String, val title: String) {

    companion object {
        const val NEWS_ID = "news_id"
        const val TOUR_ID = "tour_id"
        const val FIND_TOUR_ID = "find_tour_id"
        const val CALL_ID = "call_id"
        const val ABOUT_COMPANY_ID = "about_company_id"
        const val ABOUT_APP_ID = "about_app_id"
    }
}