package com.isanechek.balttur.fragments.appinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.onClick
import kotlinx.android.synthetic.main.custom_toolbar_layout.*

class DeveloperInfoFragment : BaseFragment() {

    override fun getLayout(): Int= _layout.developer_info_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_close_btn.apply {
            visibility = View.VISIBLE
            onClick {
                findNavController().navigateUp()
            }
        }
        toolbar_title.text = "Разработчики"
    }

}