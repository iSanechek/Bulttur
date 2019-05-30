package com.isanechek.balttur.data.models

import com.google.gson.annotations.SerializedName
import com.isanechek.balttur.data.parsers.HomePageParserImpl

data class TextContentChild(
    @SerializedName(HomePageParserImpl.JO_ERROR_STATUS_KEY) val errorStatus: Boolean = false,
    @SerializedName(HomePageParserImpl.JO_ERROR_MESSAGE_KEY) val errorMessage: String = "",
    @SerializedName("text_content") val data: List<TextContentChildData> = emptyList()
) {
    companion object {
        fun toEmpty() = TextContentChild(false, "", emptyList())
    }
}