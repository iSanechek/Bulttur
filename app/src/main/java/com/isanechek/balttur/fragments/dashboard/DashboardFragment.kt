package com.isanechek.balttur.fragments.dashboard

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.isanechek.balttur._layout
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

        val newsAdapter = DashboardNewsAdapter { item -> }
        val menuAdapter =
            DashboardMenuAdapter(DashboardMenuItem.items()) { item -> openUrl(item.data) }
        val toursAdapter =
            DashboardToursAdapter { item -> openUrl("https://balttur.spb.ru${item.bigUrl}") }
        val rootAdapter = DashboardAdapter(vm, newsAdapter, menuAdapter, toursAdapter) { callback ->
            when (callback.first) {
                "info_long_click" -> MaterialDialog(requireContext()).show {
                    title(text = "История показов")
                    listItems(items = vm.history.reversed())
                    positiveButton(text = "Закрыть") {
                        it.dismiss()
                    }
                }
                "info_short_click" -> MaterialDialog(requireContext()).show {
                    message(text = callback.second as String)
                    positiveButton(text = "Закрыть") {
                        it.dismiss()
                    }
                }
                "filter_short_click" -> Unit
                "company_info_short_click" -> MaterialDialog(requireContext())
                    .show {
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
            }
        }

        dashboard_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rootAdapter
        }

//        dashboard_fab.onClick {
//            findNavController().navigate(_id.go_from_home_to_info)
//        }
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
}