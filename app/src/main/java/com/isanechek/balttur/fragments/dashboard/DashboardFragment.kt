package com.isanechek.balttur.fragments.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import kotlinx.android.synthetic.main.custom_toolbar_layout.*
import kotlinx.android.synthetic.main.dashboard_fragment_layout.*
import kotlinx.android.synthetic.main.dashboard_news_layout_container.*

class DashboardFragment : BaseFragment() {

    private val layouts = listOf(_layout.dashboard_news_layout_container)

    override fun getLayout(): Int = _layout.dashboard_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        toolbar_title.text = "БалтТур"

        for (id in layouts) {
            createUi(id)
        }

    }

    private fun createUi(@LayoutRes id: Int) {
        val rootView = LayoutInflater.from(requireContext()).inflate(id, null)
        if (id == _layout.dashboard_news_layout_container) {
            createNewsItem(rootView)
        }
    }

    private fun createNewsItem(root: View) {

        val newsAdapter = DashboardNewsAdapter()
        newsAdapter.submit(getTestColors())
        dashboard_news_pager.adapter = newsAdapter
        dashboard_news_item_dots.setViewPager(dashboard_news_pager)


        dashboard_scroll_container.addView(root)
    }

    private fun getTestColors(): List<Int> = listOf(Color.parseColor("#F44336"), Color.parseColor("#FFEBEE"), Color.parseColor("#FFCDD2"))
}