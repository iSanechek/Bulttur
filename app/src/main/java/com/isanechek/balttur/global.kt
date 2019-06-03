package com.isanechek.balttur

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

typealias _layout = R.layout
typealias _id = R.id

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun View.onClick(function: () -> Unit) {
    setOnClickListener {
        function()
    }
}