package com.isanechek.balttur.fragments.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.database.entity.ToursInfoEntity
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.home_tours_info_item_layput.*

class ToursInfoAdapter(private val callback: (ToursInfoEntity) -> Unit) : SliderViewAdapter<ToursInfoAdapter.ToursInfoVH>() {

    private val items = mutableListOf<ToursInfoEntity>()

    override fun onCreateViewHolder(parent: ViewGroup): ToursInfoVH =
        ToursInfoVH(parent.inflate(_layout.home_tours_info_item_layput))

    override fun getCount(): Int = items.size

    override fun onBindViewHolder(holder: ToursInfoVH, position: Int) {
        holder.bindTo(items[position], callback)
    }

    fun submit(data: List<ToursInfoEntity>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ToursInfoVH(override val containerView: View) : SliderViewAdapter.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: ToursInfoEntity, callback: (ToursInfoEntity) -> Unit) {
            home_tours_item_container.onClick {
                callback(item)
            }
            home_tours_item_title.text = item.bigTitle
        }
    }
}