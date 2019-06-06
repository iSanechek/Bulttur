package com.isanechek.balttur

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

typealias _layout = R.layout
typealias _id = R.id
typealias _drawable = R.drawable

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun View.onClick(function: () -> Unit) {
    setOnClickListener {
        function()
    }
}

const val BASE_URL = "https://balttur.spb.ru"


fun View.hideWithAlpha(time: Long) {

    AlphaAnimation(1f, 0f)
        .run {
            duration = time
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    visibility = View.INVISIBLE
                }
            })
            startAnimation(this)
        }
}

fun View.showWithAlpha(time: Long) {
    AlphaAnimation(0f, 1f)
        .run {
            duration = time
            fillAfter = true
            visibility = View.VISIBLE
            startAnimation(this)
        }
}