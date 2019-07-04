package com.isanechek.balttur.fragments

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

abstract class LifecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(
    itemView
), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    fun onAppear() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    fun onDisappear() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

}