package com.isanechek.balttur.fragments.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.isanechek.balttur.*
import com.isanechek.balttur.data.models.HomeMenuItem
import com.isanechek.balttur.fragments.BaseFragment
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.home_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val vm: HomeViewModel by viewModel()

    private val data = listOf(
        HomeMenuItem(HomeMenuItem.COUNTRY_ID, "Страны", iconId = _drawable.ic_place_black_24dp),
        HomeMenuItem(HomeMenuItem.NEWS_ID, "Новости", iconId = _drawable.ic_local_library_black_24dp),
        HomeMenuItem(HomeMenuItem.TOUR_ID, "Туры", iconId = _drawable.ic_pool_black_24dp),
        HomeMenuItem(HomeMenuItem.CALL_ID, "Связь", iconId = _drawable.ic_perm_phone_msg_black_24dp),
        HomeMenuItem(HomeMenuItem.ABOUT_COMPANY_ID, "О компании", iconId = _drawable.ic_info_outline_black_24dp))

    override fun getLayout(): Int = _layout.home_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tracker.event("HomeFragment", "Открыт домашний экран.")
        setupUi()
        vm.load()
        val menuAdapter = HomeMenuAdapter { menuItem ->
            when(menuItem.id) {
                HomeMenuItem.COUNTRY_ID -> goToScreen(_id.go_from_home_to_country)
                else -> tracker.event("", "Не знаю на какой экран переходить. :(")
            }
        }
        menuAdapter.submit(data)
        with(menu_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = menuAdapter
        }
        if (platform.isLicenseShow) {
            findNavController().navigate(_id.go_from_home_to_license)
        }

        val newsAdapter = HomeNewsAdapter { newsItem ->

        }

        home_news_pager.apply {
            sliderAdapter = newsAdapter
            setIndicatorAnimation(IndicatorAnimations.COLOR)
            sliderIndicatorSelectedColor = Color.WHITE
            sliderIndicatorUnselectedColor = Color.GRAY
            setSliderTransformAnimation(SliderAnimations.CUBEOUTDEPTHTRANSFORMATION)
            scrollTimeInSec = 5
            setOnIndicatorClickListener { position ->

            }
        }

        vm.newsData.observe(this, Observer { data ->
            if (data != null) {
                newsAdapter.submit(data)
            }
        })

        val toursAdapter = ToursInfoAdapter { toursItem ->

        }

        home_tours_pager.apply {
            sliderAdapter = toursAdapter
            setIndicatorAnimation(IndicatorAnimations.COLOR)
            sliderIndicatorSelectedColor = Color.WHITE
            sliderIndicatorUnselectedColor = Color.GRAY
            setSliderTransformAnimation(SliderAnimations.CUBEOUTDEPTHTRANSFORMATION)
            scrollTimeInSec = 5
            setOnIndicatorClickListener { position ->

            }
        }

        vm.toursData.observe(this, Observer { data ->
            if (data != null) {
                toursAdapter.submit(data)
            }
        })

        vm.progressState.observe(this, Observer { status ->
            if (status) {
                home_update_btn.hideWithAlpha(150)
                home_progress.showWithAlpha(150)
            } else {
                home_progress.hideWithAlpha(150)
                home_update_btn.showWithAlpha(150)
            }
        })

        home_search_container.onClick {
            // open search screen
        }

        home_update_btn.onClick {
            vm.load(true)
        }

        home_menu_info.onClick {
            goToScreen(_id.go_from_home_to_dev)
        }
    }


    private fun goToScreen(id: Int) {
        tracker.event("HomeFragment", "OpenId $id")
        findNavController().navigate(id)
    }

    private fun setupUi() {
        home_title.showWithAlpha(250)
        home_beta.showWithAlpha(350)
        home_menu_info.showWithAlpha(270)
        home_update_btn.showWithAlpha(280)
    }

}