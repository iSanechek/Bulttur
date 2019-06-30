package com.isanechek.balttur.fragments.dashboard

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.isanechek.balttur.*
import com.isanechek.balttur.data.database.entity.NewsEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_news_item_layout.*
import java.lang.Exception

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
            dashboard_news_item_container.onClick { callback(item) }
            Picasso.get().load("$BASE_URL${item.imgUrl}").into(dashboard_news_item_iv)
//            Picasso.get().load("$BASE_URL${item.imgUrl}").into(dashboard_news_item_iv, object: Callback {
//                override fun onSuccess() {
//                    val bitmap = dashboard_news_item_iv.drawToBitmap()
//                    Palette.from(bitmap).generate {  palette ->
//                        val vibrant = palette?.darkVibrantSwatch
//                        dashboard_news_item_bg.setBackgroundColor(vibrant?.rgb ?: ContextCompat.getColor(dashboard_news_item_bg.context, _color.colorAccent))
//                        with(dashboard_news_item_title) {
//                            text = item.title
//                            setTextColor(vibrant?.titleTextColor ?: ContextCompat.getColor(dashboard_news_item_bg.context, _color.textColor))
//                        }
//
//                    }
//                }
//
//                override fun onError(e: Exception?) {
//                    dashboard_news_item_bg.setBackgroundColor(ContextCompat.getColor(dashboard_news_item_bg.context, _color.colorAccent))
//                    with(dashboard_news_item_title) {
//                        text = item.title
//                        setTextColor(ContextCompat.getColor(dashboard_news_item_bg.context, _color.textColor))
//                    }
//                }
//
//            })
        }

    }
}