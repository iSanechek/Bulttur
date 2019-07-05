package com.isanechek.balttur.fragments.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.isanechek.balttur.*
import com.isanechek.balttur.data.models.DashboardMenuItem
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.fragments.dashboard.adapters.DashboardAdapter
import com.isanechek.balttur.fragments.dashboard.adapters.DashboardMenuAdapter
import com.isanechek.balttur.fragments.dashboard.adapters.DashboardNewsAdapter
import com.isanechek.balttur.fragments.dashboard.adapters.DashboardToursAdapter
import kotlinx.android.synthetic.main.dashboard_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    private val vm: DashboardViewModel by viewModel()

    override fun getLayout(): Int = _layout.dashboard_fragment_layout

    override fun onStart() {
        super.onStart()
        vm.load()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val newsAdapter = DashboardNewsAdapter { item -> openUrl("https://balttur.spb.ru${item.link}", item.title)}
        val menuAdapter =
            DashboardMenuAdapter(DashboardMenuItem.items()) { item -> openUrl(item.data, item.title) }
        val toursAdapter =
            DashboardToursAdapter { item -> openUrl(item.bigUrl, item.bigTitle) }
        val rootAdapter = DashboardAdapter(vm, newsAdapter, menuAdapter, toursAdapter) { callback ->
            when (callback.first) {
                "info_long_click" -> MaterialDialog(requireContext()).show {
                    lifecycleOwner(this@DashboardFragment)
                    title(text = "История показов")
                    listItems(items = vm.history.reversed())
                    positiveButton(text = "Закрыть") {
                        it.dismiss()
                    }
                }
                "info_short_click" -> MaterialDialog(requireContext()).show {
                    lifecycleOwner(this@DashboardFragment)
                    message(text = callback.second as String)
                    positiveButton(text = "Закрыть") {
                        it.dismiss()
                    }
                }
                "filter_short_click" -> openUrl("https://balttur.spb.ru/filter/", "Найти тур")
                "company_info_short_click" -> MaterialDialog(requireContext())
                    .show {
                        lifecycleOwner(this@DashboardFragment)
                        title(text = "Режим работы")
                        message(
                            text = "Пн-Пт:\t11:00 – 19:00\n" +
                                    "Сб:\t12:00 – 16:00\n" +
                                    "Вс:\tвыходной"
                        )
                        positiveButton(text = "Закрыть") {
                            it.dismiss()
                        }
                    }
                "dev_info_short_click" -> openUrl(DEV_WEB_SITE, "AwerdSoft")
                "info_title_short_click" -> openUrl("https://balttur.spb.ru/about-company/about-us/", "О нас")
                "comments_short_click" -> openUrl("https://balttur.spb.ru/about-company/reviews/", "Отзывы")
            }
        }

        dashboard_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rootAdapter
        }

        dashboard_fab.onClick {
            openUrl(CONSULT_ONLINE_URL, "Онлайн консультатнт")
        }
    }

    private fun openUrl(url: String, title: String) {
        dialogUtils.showWarningBrowserDialog(requireContext(), this) { isOpen ->
            if (isOpen) {
                findNavController().navigate(
                    _id.go_from_dashboard_to_web, bundleOf(
                        Pair("args_url", url),
                        Pair("args_title", title)
                    )
                )
            }
        }
    }
}