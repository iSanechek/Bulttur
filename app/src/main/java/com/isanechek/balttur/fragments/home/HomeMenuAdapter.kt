package com.isanechek.balttur.fragments.home

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.models.HomeMenuItem
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.home_table_item_layout.*

class HomeMenuAdapter(private val callback:(HomeMenuItem) -> Unit) : RecyclerView.Adapter<HomeMenuAdapter.HomeMenuVH>() {

    private val items = mutableListOf<HomeMenuItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMenuVH =
        HomeMenuVH(parent.inflate(_layout.home_table_item_layout))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeMenuVH, position: Int) {
        holder.bindTo(items[position], callback)
    }

    fun submit(data: List<HomeMenuItem>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class HomeMenuVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: HomeMenuItem, callback:(HomeMenuItem) -> Unit) {
            home_table_container.onClick {
                callback(item)
            }
            home_table_icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.iconId))
            home_table_title.text = item.title
        }
    }
}