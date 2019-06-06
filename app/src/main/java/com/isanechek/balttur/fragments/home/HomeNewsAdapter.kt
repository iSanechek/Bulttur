package com.isanechek.balttur.fragments.home


import android.view.View
import android.view.ViewGroup
import com.isanechek.balttur.BASE_URL
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.database.entity.NewsEntity
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.home_news_item_layout.*

class HomeNewsAdapter(private val callback: (NewsEntity) -> Unit) : SliderViewAdapter<HomeNewsAdapter.HomeNewsVH>() {

    private val items = mutableListOf<NewsEntity>()

    override fun onCreateViewHolder(parent: ViewGroup): HomeNewsVH =
        HomeNewsVH(parent.inflate(_layout.home_news_item_layout))

    override fun getCount(): Int = items.size


    override fun onBindViewHolder(holder: HomeNewsVH, position: Int) {
        holder.bindTo(items[position], callback)
    }

    fun submit(data: List<NewsEntity>) {
        items.addAll(data)
        notifyDataSetChanged()
    }


    inner class HomeNewsVH(override val containerView: View) : SliderViewAdapter.ViewHolder(containerView), LayoutContainer {

        fun bindTo(item: NewsEntity, callback: (NewsEntity) -> Unit) {
            home_news_item_container.onClick {
                callback(item)
            }
            Picasso.get().load("$BASE_URL${item.imgUrl}").into(home_news_item_cover)
            home_news_item_title.text = item.title
        }

    }
}