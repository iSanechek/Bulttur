package com.isanechek.balttur.fragments.dignity

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import com.isanechek.balttur.DIGNITY_DATA
import com.isanechek.balttur.R
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.onClick
import kotlinx.android.synthetic.main.custom_toolbar_layout.*
import kotlinx.android.synthetic.main.dignity_fragment_layout.*

class DignityFragment : BaseFragment() {

    override fun getLayout(): Int = _layout.dignity_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_title.text = "Наши достойнства"
        toolbar_close_btn.apply {
            visibility = View.VISIBLE
            onClick {
                findNavController().navigateUp()
            }
        }

        for (data in DIGNITY_DATA.split(".")) {
            dignity_scroll_container.addView(TextView(ContextThemeWrapper(requireContext(), R.style.DignityTextStyle))
                .apply { text = data })
        }
    }
}