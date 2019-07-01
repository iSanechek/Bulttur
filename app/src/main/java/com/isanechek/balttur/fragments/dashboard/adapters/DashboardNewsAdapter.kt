package com.isanechek.balttur.fragments.dashboard.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur.*
import com.isanechek.balttur.data.database.entity.NewsEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_news_item_layout.*

class DashboardNewsAdapter(private val callback: (NewsEntity) -> Unit) : RecyclerView.Adapter<DashboardNewsAdapter.DashboardNewsVh>() {

    private val items = mutableListOf<NewsEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardNewsVh =
        DashboardNewsVh(parent.inflate(_layout.dashboard_news_item_layout))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DashboardNewsVh, position: Int) {
        holder.bindTo(items[position], callback)
    }

    fun submit(data: List<NewsEntity>) {
        items.addAll(data)
        notifyDataSetChanged()
    }


    inner class DashboardNewsVh(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: NewsEntity, callback: (NewsEntity) -> Unit) {
            dashboard_news_item_container.onClick {
                callback(item)
            }
//            CustomShape.bindBg(dashboard_news_item_background, "#F44336".toColorInt())
//
//            dashboard_news_item_iv.loadUrl("$BASE_URL${item.imgUrl}")
//            with(dashboard_news_item_title) {
//                text = item.title
//                setTextColor("#000000".toColorInt())
//            }

            dashboard_news_item_iv.loadUrl("$BASE_URL${item.imgUrl}") {
                onSuccess {
                    dashboard_news_item_iv.generatePalette gen@ {
                        val swatch = it?.darkMutedSwatch
                        val backgroundColor = swatch?.rgb ?: getColor(_color.defaultItemBackground)
                        val titleColor = swatch?.titleTextColor ?: getColor(_color.textDarkColor)

//                        dashboard_news_item_iv.imageTintList = ColorStateList.valueOf(backgroundColor)

//                        dashboard_news_item_background.setBackgroundColor(backgroundColor)
                        Log.e("Hyi", "color $backgroundColor")
                        CustomShape.bindBg(dashboard_news_item_background, backgroundColor)

                        with(dashboard_news_item_title) {
                            text = item.title
                            setTextColor(getColor(_color.textColor))
                        }
                    }
                }
                onError {
                    Log.e("Hyi", "color hyi")
                    dashboard_news_item_iv.imageTintList = ColorStateList.valueOf(getColor(_color.defaultItemBackground))
                    with(dashboard_news_item_title) {
                        text = item.title
                        setTextColor("000000".toColorInt())
                    }
                }
            }
        }

        private fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(itemView.context, id)

    }
}