package com.isanechek.balttur.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val vm: HomeViewModel by viewModel()

    override fun getLayout(): Int = _layout.home_fragment_layout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.load()
        Log.e("HYI", "Fragment")
    }

}