package com.isanechek.balttur.fragments.connect

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.isanechek.balttur.OPEN_SCREEN_TAG
import com.isanechek.balttur.actionView
import com.isanechek.balttur.fragments.BaseListFragment
import com.isanechek.balttur.onClick
import kotlinx.android.synthetic.main.base_list_fragment_layout.*
import kotlinx.android.synthetic.main.custom_toolbar_layout.*

class ConnectFragment : BaseListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tracker.event(OPEN_SCREEN_TAG, "Открыт экран с тел номерами")

        toolbar_close_btn.apply {
            visibility = View.VISIBLE
            onClick {
                tracker.event(OPEN_SCREEN_TAG, "Закрыт экран с тел номерами")
                findNavController().navigateUp()
            }
        }
        toolbar_title.text = "Позвонить"

        val connectAdapter = ConnectAdapter { item ->
            when {
                prefUtils.isLongShowWarning -> {
                    dialogUtils.showWarningLongDialog(requireContext(), this@ConnectFragment) { isOk ->
                        if (isOk) {
                            prefUtils.isLongShowWarning = false
                            startAction(item.second, item.first)
                        }
                    }
                }
                else -> startAction(item.second, item.first)
            }
        }

        with(base_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = connectAdapter
        }
    }

    private fun startAction(data: String, action: String) {
        if (action.contains("long")) {
            copyDataAction(data, "TelNumber", "Скопирован номер $data")
        } else if (action.contains("tel")) {
            MaterialDialog(requireContext()).show {
                lifecycleOwner(this@ConnectFragment)
                title(text = "Предупреждение")
                message(text = "Вы собираетесь осуществить вызов на номер С.Петербурга!")
                positiveButton(text = "Продолжить") {
                    tracker.event("TelNumber", "Был сделан вызов по номеру $data")
                    requireActivity().actionView { data }
                    it.dismiss()
                }
                negativeButton(text = "Отменить") {
                    tracker.event("TelNumber", "Был отменен вызов по номеру $data")
                    it.dismiss()
                }
            }
        }
    }
}