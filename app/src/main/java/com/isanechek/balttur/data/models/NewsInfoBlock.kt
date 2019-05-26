package com.isanechek.balttur.data.models

data class NewsInfoBlock(val content: String, val type: String) {
    companion object {
        const val TYPE_LIST = "list"
        const val TYPE_STRING = "string"
    }
}