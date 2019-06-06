package com.isanechek.balttur.data.models

data class TextContent(
    val content: String = "",
    val contents: TextContentChild = TextContentChild.toEmpty(),
    val type: String = ""
) {

    companion object {
        const val LIST = "list"
        const val STRING = "string"
    }
}