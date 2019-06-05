package com.isanechek.balttur.fragments.country

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur._layout
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_item_layout.*

class CountryAdapter(private val callback:(Pair<String, String>) -> Unit) : RecyclerView.Adapter<CountryAdapter.CountryVH>()

{

    private val items = mutableListOf<Pair<String, String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryVH =
        CountryVH(parent.inflate(_layout.country_item_layout))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CountryVH, position: Int) {
        holder.bindTo(items[position], callback)
    }

    fun submit(data: List<Pair<String, String>>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class CountryVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: Pair<String, String>, callback:(Pair<String, String>) -> Unit) {
            country_item_title.text = item.first
            country_item_container.onClick {
                callback(item)
            }

        }
    }
}