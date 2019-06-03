package com.isanechek.balttur.fragments.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.models.HomeMenuItem
import com.isanechek.balttur.featuredrecyclerview.FeatureRecyclerViewAdapter
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.home_item_layout.*
import android.R.attr.data



class HomeAdapter(private val callback: (HomeMenuItem) -> Unit) : FeatureRecyclerViewAdapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<HomeMenuItem>()

    override fun onCreateFeaturedViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        ITEM_TYPE_FEATURED -> MenuHolder(parent.inflate(_layout.home_item_layout))
        else -> MenuDummyHolder(parent.inflate(_layout.home_dummy_item))
    }

    override fun onBindFeaturedViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is MenuHolder) {
            holder.bindTo(items[position], callback)
        } else if (holder is MenuDummyHolder) {
            //Do nothing
        }
    }

    override fun getFeaturedItemsCount(): Int = items.size + 2

    override fun onSmallItemResize(holder: RecyclerView.ViewHolder?, position: Int, offset: Float) {
        if (holder is MenuHolder) {
            holder.home_menu_item_title.alpha = offset / 100f
        }
    }

    override fun onBigItemResize(holder: RecyclerView.ViewHolder?, position: Int, offset: Float) {
        if (holder is MenuHolder) {
            holder.home_menu_item_title.alpha = offset / 100f
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position >= 0 && position < items.size) ITEM_TYPE_FEATURED else ITEM_TYPE_DUMMY

    fun submit(data: List<HomeMenuItem>) {
        items.addAll(data)
        notifyDataSetChanged()
    }


    inner class MenuHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: HomeMenuItem, callback: (HomeMenuItem) -> Unit) {
            home_menu_item_container.onClick {
                callback(item)
            }
            home_menu_item_title.text = item.title
        }

    }

    inner class MenuDummyHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    }

    companion object {
        private const val ITEM_TYPE_FEATURED = 0
        private const val ITEM_TYPE_DUMMY = 1
    }

}