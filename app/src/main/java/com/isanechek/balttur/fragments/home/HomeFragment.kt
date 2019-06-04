package com.isanechek.balttur.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.isanechek.balttur._drawable
import com.isanechek.balttur._id
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.models.HomeMenuItem
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.onClick
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
        createTablesUi()
        if (platform.isLicenseShow) {
            findNavController().navigate(_id.go_from_home_to_license)
        }
    }


    private fun createTablesUi() {
        for (i in 0 until data.size) {
            val item = data[i]
            val root = LayoutInflater.from(requireContext()).inflate(_layout.home_table_item_layout, null)
            val container = root.findViewById<FrameLayout>(_id.home_table_container)
            container.onClick {

            }
            val icon = root.findViewById<ImageView>(_id.home_table_icon)
            icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), item.iconId))
            val title = root.findViewById<TextView>(_id.home_table_title)
            title.text = item.title

            val card = MaterialCardView(requireContext())
            val mp = FrameLayout.LayoutParams.MATCH_PARENT
            val p = LinearLayout.LayoutParams(mp, mp, 1f)
            p.setMargins(4, 4, 4, 4)
            card.layoutParams = p
            card.radius = 8f
            card.addView(root)


            if (i > 2) {
                home_table_top_container.addView(card)
            } else {
                home_table_bottom_container.addView(card)
            }
        }
    }

}