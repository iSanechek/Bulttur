package com.isanechek.balttur.data.models

data class TextContentChild(
    val errorStatus: Boolean = false,
    val errorMessage: String = "",
    val data: List<TextContentChildData> = emptyList()
) {
    companion object {
        fun toEmpty() = TextContentChild(false, "", emptyList())
    }
}