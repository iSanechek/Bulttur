package com.isanechek.balttur.data

interface PageParser<T> {
    suspend fun parse(source: String): T
}