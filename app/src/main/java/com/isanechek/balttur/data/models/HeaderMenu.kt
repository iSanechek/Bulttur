package com.isanechek.balttur.data.models

data class HeaderMenu(val title: String, val url: String, val countries: Set<Countries>, val subMenus: Set<ItemMenu>)