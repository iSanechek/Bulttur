package com.isanechek.balttur.utils

import org.json.JSONObject

fun Any.toJson(key: String): JSONObject = JSONObject().apply { put(key, this) }

fun createJO(vararg items: Pair<String, Any>): JSONObject {
    val jo = JSONObject()
    for (item in items) jo.put(item.first, item.second)
    return jo
}

fun Any.toPair(key: String): Pair<String, Any> = Pair(key, this)