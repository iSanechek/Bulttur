package com.isanechek.balttur.fragments.dashboard.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.database.entity.ToursInfoEntity
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_tours_item_layout.*

class DashboardToursAdapter(private val callback: (ToursInfoEntity) -> Unit) : RecyclerView.Adapter<DashboardToursAdapter.DashboardToursVH>() {

    private val items = mutableListOf<ToursInfoEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardToursVH =
        DashboardToursVH(parent.inflate(_layout.dashboard_tours_item_layout))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DashboardToursVH, position: Int) {
        holder.bindTo(items[position], callback)
    }

    fun submit(data: List<ToursInfoEntity>) {
        items.addAll(data.shuffled())
        notifyDataSetChanged()
    }

    inner class DashboardToursVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: ToursInfoEntity, callback: (ToursInfoEntity) -> Unit) {
            dashboard_tours_item_description.onClick { callback(item) }
            dashboard_tours_item_price.text = item.price
            dashboard_tours_item_title.text = item.bigTitle
        }
    }
}