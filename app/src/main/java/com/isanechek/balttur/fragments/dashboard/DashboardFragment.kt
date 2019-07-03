package com.isanechek.balttur.fragments.dashboard

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.isanechek.balttur.*
import com.isanechek.balttur.data.models.DashboardMenuItem
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.fragments.dashboard.adapters.DashboardMenuAdapter
import com.isanechek.balttur.fragments.dashboard.adapters.DashboardNewsAdapter
import com.isanechek.balttur.fragments.dashboard.adapters.DashboardToursAdapter
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import kotlinx.android.synthetic.main.dashboard_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    private val vm: DashboardViewModel by viewModel()

    private val layouts = listOf(
        _layout.dashboard_news_layout_container,
        _layout.dashboard_info_layout_container,
        _layout.dashboard_menu_layout_container,
        _layout.dashboard_tours_item_layout_container)


    override fun getLayout(): Int = _layout.dashboard_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        for (id in layouts) {
            createUi(id)
        }

        dashboard_fab.onClick {
            findNavController().navigate(_id.go_from_home_to_info)
        }
    }

    private fun createUi(@LayoutRes id: Int) {
        val rootView = LayoutInflater.from(requireContext()).inflate(id, null)
        when (id) {
            _layout.dashboard_news_layout_container -> createNewsItem(rootView)
            _layout.dashboard_info_layout_container -> createInfoItem(rootView)
            _layout.dashboard_menu_layout_container -> createMenuItem(rootView)
            _layout.dashboard_tours_item_layout_container -> createToursItem(rootView)
        }
        vm.load()
    }


    private fun createToursItem(root: View) {

        val toursAdapter = DashboardToursAdapter { item ->
            openUrl("https://balttur.spb.ru${item.bigUrl}")
        }
        val pager = root.findViewById<ViewPager2>(_id.dashboard_tours_item_pager)
        pager.adapter = toursAdapter
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val next = root.findViewById<ImageButton>(_id.dashboard_tours_item_next)
        val prev = root.findViewById<ImageButton>(_id.dashboard_tours_item_prev)
        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val size = toursAdapter.itemCount
                if (size > 1) {

                }
            }
        })

        vm.toursData.observe(this, Observer { data ->
            if (data != null) {
                toursAdapter.submit(data)
            }
        })

        root.layoutParams = getParams(bottom = 0, height = 200.px)
        dashboard_scroll_container.addView(root)
    }

    private fun createMenuItem(root: View) {

        val menuAdapter = DashboardMenuAdapter(DashboardMenuItem.items()) { item ->
            openUrl(item.data)
        }
        root.findViewById<RecyclerView>(_id.dashboard_menu_items).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = menuAdapter
        }
        root.layoutParams = getParams(left = 0, top = 16, right = 0, bottom = 8, height = -2)
        dashboard_scroll_container.addView(root)
    }



    private fun createInfoItem(root: View) {
        root.layoutParams = getParams(height = 136.px)
        val switcher = root.findViewById<TextSwitcher>(_id.dashboard_info_item_switcher)
        switcher.apply {
            setFactory { TextView(ContextThemeWrapper(requireContext(), R.style.DashboardInfoSubTitle)) }
            animateFirstView = false
            inAnimation = AnimationUtils.loadAnimation(requireContext(), _anim.slide_down)
            outAnimation = AnimationUtils.loadAnimation(requireContext(), _anim.move_right)
            setCurrentText("Автобусные туры по Европе")
            onClick {
                MaterialDialog(requireContext()).show {
                    message(text = (switcher.currentView as TextView).text)
                    positiveButton(text = "Закрыть") {
                        it.dismiss()
                    }
                }
            }
            setOnLongClickListener {
                MaterialDialog(requireContext()).show {
                    title(text = "История показов")
                    listItems(items = vm.history.reversed())
                    positiveButton(text = "Закрыть") {
                        it.dismiss()
                    }
                }
                true
            }
        }
        vm.loadInfoData().observe(this, Observer { text ->
            if (text != null && switcher != null) {
                switcher.setText(text)
            }
        })
        dashboard_scroll_container.addView(root)
    }


    private fun createNewsItem(root: View) {
        val pager = root.findViewById<ViewPager2>(_id.dashboard_news_pager)
        val dots = root.findViewById<SpringDotsIndicator>(_id.dashboard_news_item_dots)
        val newsAdapter = DashboardNewsAdapter { item ->
            openUrl("https://balttur.spb.ru${item.link}")
        }
        pager.adapter = newsAdapter
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        dots.setViewPager(pager)
        dashboard_scroll_container.addView(root)
        vm.newsData.observe(this, Observer { data ->
            if (data != null) {
                newsAdapter.submit(data)
            }
        })
    }

    private fun openUrl(url: String) {
        dialogUtils.showWarningBrowserDialog(requireContext()) { isOpen ->
            if (isOpen) {
                openTab(
                    requireContext(),
                    url
                )
            }
        }
    }

    private fun getParams(left: Int = 16, top: Int = 16, right: Int = 16, bottom: Int = 16, height: Int): LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        params.setMargins(left, top, right, bottom)
        return params
    }
}