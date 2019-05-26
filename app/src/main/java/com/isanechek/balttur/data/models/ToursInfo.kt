package com.isanechek.balttur.data.models

data class ToursInfo(val groupTitle: String,
                     val groupUrl: String,
                     val bigTitle: String,
                     val bigUrl: String,
                     val price: String,
                     val description: String,
                     val daysTitle: String,
                     val dates: Set<String>)