package com.isanechek.balttur.data.models

import com.google.gson.annotations.SerializedName

data class TextContent(
    @SerializedName("content") val content: String = "",
    @SerializedName("contents") val contents: TextContentChild = TextContentChild.toEmpty(),
    @SerializedName("type") val type: String = ""
) {

    companion object {
        const val LIST = "list"
        const val STRING = "string"
    }
}