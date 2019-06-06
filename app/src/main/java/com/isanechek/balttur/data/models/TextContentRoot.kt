package com.isanechek.balttur.data.models

data class TextContentRoot(val errorStatus: Boolean = false,
                           val errorMessage: String = "",
                           val items: List<TextContent> = emptyList())