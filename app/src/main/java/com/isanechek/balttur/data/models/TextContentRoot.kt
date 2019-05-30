package com.isanechek.balttur.data.models

import com.google.gson.annotations.SerializedName
import com.isanechek.balttur.data.parsers.HomePageParserImpl

data class TextContentRoot(@SerializedName(HomePageParserImpl.JO_ERROR_STATUS_KEY) val errorStatus: Boolean = false,
                           @SerializedName(HomePageParserImpl.JO_ERROR_MESSAGE_KEY) val errorMessage: String = "",
                           @SerializedName("content") val items: List<TextContent> = emptyList())