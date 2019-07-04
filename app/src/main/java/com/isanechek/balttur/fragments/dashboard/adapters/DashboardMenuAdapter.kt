package com.isanechek.balttur.fragments.dashboard.adapters

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.models.DashboardMenuItem
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_menu_item_layout.*

class DashboardMenuAdapter(
    private val items: List<DashboardMenuItem>,
    private val callback: (DashboardMenuItem) -> Unit
) : RecyclerView.Adapter<DashboardMenuAdapter.DashboardMenuVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuVH =
        DashboardMenuVH(parent.inflate(_layout.dashboard_menu_item_layout))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DashboardMenuVH, position: Int) {
        holder.bindTo(items[position], callback)
    }

    inner class DashboardMenuVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: DashboardMenuItem, callback: (DashboardMenuItem) -> Unit) {
            dashboard_menu_item_container.onClick { callback(item) }
            dashboard_menu_item_container.setCardBackgroundColor(item.color.toColorInt())
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.OVAL
            shape.setColor(item.secondColor.toColorInt())
            dashboard_menu_item_icon_bg.background = shape
            dashboard_menu_item_icon.setImageDrawable(ContextCompat.getDrawable(containerView.context, item.iconId))
            dashboard_menu_item_title.text = item.title
        }
    }
}