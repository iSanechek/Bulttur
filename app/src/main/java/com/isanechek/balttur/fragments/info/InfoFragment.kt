package com.isanechek.balttur.fragments.info

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.onClick
import kotlinx.android.synthetic.main.info_fraagment_layout.*

class InfoFragment : BaseFragment() {

    override fun getLayout(): Int = _layout.info_fraagment_layout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        info_fragment_close_btn.onClick {
            findNavController().navigateUp()
        }
    }
}