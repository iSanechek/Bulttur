package com.isanechek.balttur.fragments.dashboard

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur._layout
import com.isanechek.balttur.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_news_item_layout.*

class DashboardNewsAdapter : RecyclerView.Adapter<DashboardNewsAdapter.DashboardNewsVh>() {

    private val colors = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardNewsVh =
        DashboardNewsVh(parent.inflate(_layout.dashboard_news_item_layout))

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: DashboardNewsVh, position: Int) {
        holder.bindTo(colors[position])
    }

    fun submit(data: List<Int>) {
        colors.addAll(data)
        notifyDataSetChanged()
    }


    inner class DashboardNewsVh(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(color: Int) {

            test_news_color.setBackgroundColor(color)

        }

    }
}