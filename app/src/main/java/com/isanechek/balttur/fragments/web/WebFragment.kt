package com.isanechek.balttur.fragments.web

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.isanechek.balttur.R
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.onClick
import com.izikode.izilib.veinview.defaultClient
import kotlinx.android.synthetic.main.custom_toolbar_layout.*
import kotlinx.android.synthetic.main.web_fragment_layout.*

class WebFragment : BaseFragment() {

    private val url: String
        get() = arguments?.getString("args_url") ?: ""
    private val title: String
        get() = arguments?.getString("args_title") ?: ""

    override fun getLayout(): Int = _layout.web_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        web_vein_view.setVeinViewClient(defaultClient { injector, page ->
            injector.injectCSS(R.raw.dark_style)
        })
        web_vein_view.settings.apply {
            loadWithOverviewMode = true
            useWideViewPort = true
        }
        if (url.isNotEmpty()) {
            web_vein_view.loadUrl(url)
        }

        toolbar_close_btn.apply {
            visibility = View.VISIBLE
            onClick {
                findNavController().navigateUp()
            }
        }
        toolbar_title.text = title
    }

}