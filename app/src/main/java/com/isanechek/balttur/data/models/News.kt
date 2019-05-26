package com.isanechek.balttur.data.models

data class News(val imgUrl: String,
                val date: String,
                val title: String,
                val titleUrl: String,
                val link: String,
                val linkDescription: String,
                val infoBlock: NewsInfoBlock)