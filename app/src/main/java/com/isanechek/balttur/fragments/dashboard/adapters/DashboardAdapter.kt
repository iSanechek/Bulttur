package com.isanechek.balttur.fragments.dashboard.adapters

import android.annotation.SuppressLint
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
import com.astritveliu.boom.Boom
import com.isanechek.balttur.*
import com.isanechek.balttur.fragments.LifecycleViewHolder
import com.isanechek.balttur.fragments.dashboard.DashboardViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.comments_item_layout_container.*
import kotlinx.android.synthetic.main.dashboard_company_item_layout_container.*
import kotlinx.android.synthetic.main.dashboard_filter_tours_layout_container.*
import kotlinx.android.synthetic.main.dashboard_info_layout_container.*
import kotlinx.android.synthetic.main.dashboard_menu_layout_container.*
import kotlinx.android.synthetic.main.dashboard_news_layout_container.*
import kotlinx.android.synthetic.main.dashboard_social_item_layout_container.*
import kotlinx.android.synthetic.main.dashboard_tours_item_layout_container.*
import kotlinx.android.synthetic.main.empty_layout_container.*

class DashboardAdapter(
    private val vm: DashboardViewModel,
    private val newsAdapter: DashboardNewsAdapter,
    private val menuAdapter: DashboardMenuAdapter,
    private val toursAdapter: DashboardToursAdapter,
    private val callback: (Pair<String, Any>) -> Unit
) :
    RecyclerView.Adapter<LifecycleViewHolder>() {

    private val items = listOf("news", "menu", "info", "tours", "filter", "company_info", "social", "comments", "empty")

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
            EMPTY_TYPE -> EmptyHolder(parent.inflate(_layout.empty_layout_container))
            COMMENTS_TYPE -> CommentsHolder(parent.inflate(_layout.comments_item_layout_container))
            SOCIAL_TYPE -> SocialHolder(parent.inflate(_layout.dashboard_social_item_layout_container))
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
            is EmptyHolder -> holder.bindTo(callback)
            is CommentsHolder -> holder.bindTo(callback)
            is SocialHolder -> holder.bindTo(callback)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        "news" -> NEWS_TYPE
        "menu" -> MENU_TYPE
        "info" -> INFO_TYPE
        "tours" -> TOURS_TYPE
        "filter" -> FILTER_TYPE
        "company_info" -> COMPANY_INFO_TYPE
        "empty" -> EMPTY_TYPE
        "comments" -> COMMENTS_TYPE
        "social" -> SOCIAL_TYPE
        else -> EMPTY_TYPE
    }

    inner class CommentsHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(callback: (Pair<String, Any>) -> Unit) {
            comments_item_container.apply {
                Boom(this)
                onClick { callback(Pair("comments_short_click", "")) }
            }
        }
    }

    inner class SocialHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(callback: (Pair<String, Any>) -> Unit) {
            social_item_vk_btn.apply {
                Boom(this)
                onClick { callback(Pair("open_vk", "https://vk.com/baltturs")) }
                setOnLongClickListener {
                    callback(Pair("copy_vk", "https://vk.com/baltturs"))
                    true
                }
            }
            social_item_email_btn.apply {
                Boom(this)
                onClick { callback(Pair("email_open", "secretary@tourpom.ru")) }
                setOnLongClickListener {
                    callback(Pair("email_copy", "secretary@tourpom.ru"))
                    true
                }
            }
        }
    }

    inner class CompanyInfoHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(callback: (Pair<String, Any>) -> Unit) {
            connect_item_call_container.apply {
                onClick { callback(Pair("company_call_short_click", "")) }
                Boom(this)
            }
            connect_item_map_container.apply {
                onClick { callback(Pair("company_map_short_click", "")) }
                Boom(this)
            }
            connect_item_work_time_container.apply {
                onClick { callback(Pair("company_work_time_short_click", "")) }
                Boom(this)
            }
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
            dashboard_tours_item_dots.setViewPager(dashboard_tours_item_pager)
            vm.toursData.observe(this, Observer { data ->
                if (data != null) {
                    toursAdapter.submit(data)
                }
            })
            
            vm.progressState.observe(this, Observer { state ->
                when {
                    state -> dashboard_tours_item_progress.visibility = View.VISIBLE
                    else -> dashboard_tours_item_progress.visibility = View.INVISIBLE
                }
            })
        }
    }

    inner class FilterHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(callback: (Pair<String, Any>) -> Unit) {
            Boom(filter_tours_container)
            filter_tours_container.onClick {
                callback(Pair("filter_short_click", "empty"))
            }
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
            dashboard_info_item_title.apply {
                Boom(this)
                onClick { callback(Pair("info_title_short_click", "")) }
            }
        }
    }

    inner class MenuHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        fun bindTo(menuAdapter: DashboardMenuAdapter) {
            dashboard_menu_items.apply {
                layoutManager =
                    LinearLayoutManager(containerView.context, RecyclerView.HORIZONTAL, false)
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
            vm.newsData.observe(this, Observer { data ->
                if (data != null) newsAdapter.submit(data)
            })

            vm.progressState.observe(this, Observer { state ->
                when {
                    state -> dashboard_news_item_progress.visibility = View.VISIBLE
                    else -> dashboard_news_item_progress.visibility = View.INVISIBLE
                }
            })
        }
    }

    inner class EmptyHolder(override val containerView: View) :
        LifecycleViewHolder(containerView),
        LayoutContainer {
        @SuppressLint("SetTextI18n")
        fun bindTo(callback: (Pair<String, Any>) -> Unit) {
            Boom(awerd_app_version_container)
            awerd_app_version_container.apply {
                Boom(this)
                onClick { callback(Pair("dev_info_short_click", "empty")) }
            }
            awerd_app_version_tv.text = "v:${BuildConfig.VERSION_NAME}"
        }
    }

    companion object {
        private const val EMPTY_TYPE = 0
        private const val NEWS_TYPE = 1
        private const val MENU_TYPE = 2
        private const val INFO_TYPE = 3
        private const val TOURS_TYPE = 4
        private const val FILTER_TYPE = 5
        private const val COMPANY_INFO_TYPE = 6
        private const val COMMENTS_TYPE = 7
        private const val SOCIAL_TYPE = 8
    }
}