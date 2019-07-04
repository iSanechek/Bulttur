package com.isanechek.balttur.fragments.dashboard.adapters

import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.isanechek.balttur.*
import com.isanechek.balttur.fragments.LifecycleViewHolder
import com.isanechek.balttur.fragments.dashboard.DashboardViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dashboard_company_item_layout_container.*
import kotlinx.android.synthetic.main.dashboard_filter_tours_layout_container.*
import kotlinx.android.synthetic.main.dashboard_info_layout_container.*
import kotlinx.android.synthetic.main.dashboard_menu_layout_container.*
import kotlinx.android.synthetic.main.dashboard_news_layout_container.*
import kotlinx.android.synthetic.main.dashboard_tours_item_layout_container.*

class DashboardAdapter(
    private val vm: DashboardViewModel,
    private val newsAdapter: DashboardNewsAdapter,
    private val menuAdapter: DashboardMenuAdapter,
    private val toursAdapter: DashboardToursAdapter,
    private val callback: (Pair<String, Any>) -> Unit
) :
    RecyclerView.Adapter<LifecycleViewHolder>() {

    private val items = listOf("news", "menu", "info", "tours", "filter", "company_info")

    override fun onViewAttachedToWindow(holder: LifecycleViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppear()
    }

    override fun onViewDetachedFromWindow(holder: LifecycleViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifecycleViewHolder =
        when (viewType) {
            NEWS_TYPE -> NewsContainerHolder(parent.inflate(_layout.dashboard_news_layout_container))
            MENU_TYPE -> MenuHolder(parent.inflate(_layout.dashboard_menu_layout_container))
            INFO_TYPE -> InfoHolder(parent.inflate(_layout.dashboard_info_layout_container))
            TOURS_TYPE -> ToursHolder(parent.inflate(_layout.dashboard_tours_item_layout_container))
            FILTER_TYPE -> FilterHolder(parent.inflate(_layout.dashboard_filter_tours_layout_container))
            COMPANY_INFO_TYPE -> CompanyInfoHolder(parent.inflate(_layout.dashboard_company_item_layout_container))
            else -> EmptyHolder(FrameLayout(parent.context))
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LifecycleViewHolder, position: Int) {
        when (holder) {
            is NewsContainerHolder -> holder.bindTo(vm, newsAdapter)
            is MenuHolder -> holder.bindTo(menuAdapter)
            is InfoHolder -> holder.bindTo(vm, callback)
            is ToursHolder -> holder.bindTo(vm, toursAdapter)
            is FilterHolder -> holder.bindTo(callback)
            is CompanyInfoHolder -> holder.bindTo(callback)
        }
    }

    override fun getItemViewType(position: Int): Int = when(items[position]) {
        "news" -> NEWS_TYPE
        "menu" -> MENU_TYPE
        "info" -> INFO_TYPE
        "tours" -> TOURS_TYPE
        "filter" -> FILTER_TYPE
        "company_info" -> COMPANY_INFO_TYPE
        else -> EMPTY_TYPE
    }

    inner class CompanyInfoHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(callback: (Pair<String, Any>) -> Unit) {
            connect_item_call_container.onClick { callback(Pair("", "")) }
            connect_item_map_container.onClick { callback(Pair("", "")) }
            connect_item_work_time_container.onClick { callback(Pair("company_info_short_click", "")) }
        }
    }

    inner class ToursHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(vm: DashboardViewModel, toursAdapter: DashboardToursAdapter) {
            dashboard_tours_item_pager.apply {
                adapter = toursAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            vm.toursData.observe(this, Observer { data ->
                if (data != null) {
                    toursAdapter.submit(data)
                }
            })
        }
    }

    inner class FilterHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(callback: (Pair<String, Any>) -> Unit) {
            filter_tours_container.onClick { callback(Pair("filter_short_click", "empty")) }
        }
    }

    inner class InfoHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(vm: DashboardViewModel, callback: (Pair<String, Any>) -> Unit) {
            val ctx = containerView.context
            dashboard_info_item_switcher.apply {
                setFactory {
                    TextView(
                        ContextThemeWrapper(
                            ctx,
                            R.style.DashboardInfoSubTitle
                        )
                    )
                }
                animateFirstView = true
                inAnimation = AnimationUtils.loadAnimation(ctx, _anim.slide_down)
                outAnimation = AnimationUtils.loadAnimation(ctx, _anim.move_right)
                setCurrentText("Автобусные туры по Европе")
                onClick {
                    callback(Pair("info_short_click", (this.currentView as TextView).text))
                }
                setOnLongClickListener {
                    callback(Pair("info_long_click", "empty"))
                    true
                }
            }
            vm.loadInfoData().observe(this, Observer { text ->
                if (text != null) {
                    dashboard_info_item_switcher.setText(text)
                }
            })
        }
    }

    inner class MenuHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(menuAdapter: DashboardMenuAdapter) {
            dashboard_menu_items.apply {
                layoutManager = LinearLayoutManager(containerView.context, RecyclerView.HORIZONTAL, false)
                adapter = menuAdapter
                setHasFixedSize(true)
            }
        }
    }

    inner class NewsContainerHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(vm: DashboardViewModel, newsAdapter: DashboardNewsAdapter) {
            dashboard_news_pager.apply {
                adapter = newsAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            dashboard_news_item_dots.setViewPager(dashboard_news_pager)
            vm.newsData.observe(this, Observer {data ->
                if (data != null) newsAdapter.submit(data)
            })
        }
    }

    inner class EmptyHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer

    companion object {
        private const val EMPTY_TYPE = 0
        private const val NEWS_TYPE = 1
        private const val MENU_TYPE = 2
        private const val INFO_TYPE = 3
        private const val TOURS_TYPE = 4
        private const val FILTER_TYPE = 5
        private const val COMPANY_INFO_TYPE = 6
    }
}