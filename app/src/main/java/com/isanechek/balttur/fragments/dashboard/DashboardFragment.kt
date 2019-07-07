package com.isanechek.balttur.fragments.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.google.android.material.snackbar.Snackbar
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
        val menuAdapter = DashboardMenuAdapter(DashboardMenuItem.items()) { item ->
            when {
                item.data.contains("dev") -> findNavController().navigate(_id.go_from_dashboard_to_dev_info)
                else -> openUrl(item.data, item.title)
            }
        }
        val toursAdapter = DashboardToursAdapter { item -> openUrl(item.bigUrl, item.bigTitle) }
        val rootAdapter = DashboardAdapter(vm, newsAdapter, menuAdapter, toursAdapter) { callback ->
            when (callback.first) {
                "info_long_click" -> MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
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
                "company_work_time_short_click" -> MaterialDialog(requireContext())
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
                "dev_info_short_click" -> {
                    requireActivity().actionView { DEV_WEB_SITE }
                }
                "info_title_short_click" -> openUrl("https://balttur.spb.ru/about-company/about-us/", "О нас")
                "comments_short_click" -> openUrl("https://balttur.spb.ru/about-company/reviews/", "Отзывы")
                "company_map_short_click" -> handleAction(MAPS_LIN, "Переход в карты")
                "company_call_short_click" -> findNavController().navigate(_id.go_from_dashboard_to_connect)
                "open_vk" -> handleAction(callback.second as String, "Переход в вк")
                "copy_vk" -> copyDataAction(callback.second as String, CLICK_EVENT, "Скопирована вк ссылка")
                "email_open" -> {
                    requireActivity().sendEmail("БалтТур", callback.second as String, "")
                    tracker.event(CLICK_EVENT, "Открыт e-mail ${callback.second}")
                }
                "email_copy"  -> copyDataAction(callback.second as String, CLICK_EVENT, "Скопирована e-mail")
            }
        }

        dashboard_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rootAdapter
        }

        dashboard_fab.onClick {
            openUrl(CONSULT_ONLINE_URL, "Онлайн консультатнт")
        }

        vm.errorMessage.observe(this, Observer { error ->
            if (error != null) {
                Snackbar
                    .make(dashboard_app_coordinator, "Упс... При загрузке что-то пошло не так", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Повторить") { vm.load() }
                    .show()
            }
        })
    }

    private fun handleAction(data: String, msg: String) {
        if (prefUtils.isLongShowWarning) {
            dialogUtils.showWarningLongDialog(requireContext(), this) {isOk ->
                if (isOk) {
                    prefUtils.isLongShowWarning = false
                    startAction(data, CLICK_EVENT, msg)
                }
            }
        } else startAction(data, CLICK_EVENT, msg)
    }

    private fun openUrl(url: String, title: String) {
        dialogUtils.showWarningBrowserDialog(requireContext(), this) { isOpen ->
            clickEvent("Открыто $title")
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