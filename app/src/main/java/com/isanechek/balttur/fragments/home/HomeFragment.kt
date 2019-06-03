package com.isanechek.balttur.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.isanechek.balttur._id
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.models.HomeMenuItem
import com.isanechek.balttur.featuredrecyclerview.FeatureLinearLayoutManager
import com.isanechek.balttur.fragments.BaseFragment
import kotlinx.android.synthetic.main.home_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val vm: HomeViewModel by viewModel()

    private val data = listOf(
        HomeMenuItem(HomeMenuItem.NEWS_ID, "Новости"),
        HomeMenuItem(HomeMenuItem.TOUR_ID, "Туры"),
        HomeMenuItem(HomeMenuItem.FIND_TOUR_ID, "Поиск туров"),
        HomeMenuItem(HomeMenuItem.CALL_ID, "Связь"),
        HomeMenuItem(HomeMenuItem.ABOUT_COMPANY_ID, "О компании"))

    override fun getLayout(): Int = _layout.home_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeAdapter = HomeAdapter { callback ->

        }

        with(home_screen_list) {
            layoutManager = FeatureLinearLayoutManager(requireContext())
            adapter = homeAdapter
        }

        homeAdapter.submit(data)
        if (!platform.isLicenseShow) {
            findNavController().navigate(_id.go_from_home_to_license)
        }

    }


}