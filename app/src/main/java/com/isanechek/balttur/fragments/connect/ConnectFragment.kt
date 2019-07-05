package com.isanechek.balttur.fragments.connect

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.isanechek.balttur.fragments.BaseListFragment
import com.isanechek.balttur.onClick
import kotlinx.android.synthetic.main.base_list_fragment_layout.*
import kotlinx.android.synthetic.main.custom_toolbar_layout.*

class ConnectFragment : BaseListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_close_btn.apply {
            visibility = View.VISIBLE
            onClick {
                findNavController().navigateUp()
            }
        }
        toolbar_title.text = "Позвонить"

        val connectAdapter = ConnectAdapter { item -> }

        with(base_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = connectAdapter
        }
    }

}