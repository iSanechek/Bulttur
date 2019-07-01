package com.isanechek.balttur.fragments.home

import android.view.View
import android.view.ViewGroup
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.database.entity.ToursInfoEntity
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_tours_item_layout.*

class ToursInfoAdapter(private val callback: (ToursInfoEntity) -> Unit) : SliderViewAdapter<ToursInfoAdapter.ToursInfoVH>() {

    private val items = mutableListOf<ToursInfoEntity>()

    override fun onCreateViewHolder(parent: ViewGroup): ToursInfoVH =
        ToursInfoVH(parent.inflate(_layout.dashboard_tours_item_layout))

    override fun getCount(): Int = items.size

    override fun onBindViewHolder(holder: ToursInfoVH, position: Int) {
        holder.bindTo(items[position], callback)
    }

    fun submit(data: List<ToursInfoEntity>) {
        if (items.isNotEmpty()) {
            items.clear()
        }
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ToursInfoVH(override val containerView: View) : SliderViewAdapter.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: ToursInfoEntity, callback: (ToursInfoEntity) -> Unit) {
            dashboard_tours_item_description.onClick {
                callback(item)
            }
            dashboard_tours_item_title.text = item.bigTitle
            dashboard_tours_item_price.text = item.price
        }
    }
}