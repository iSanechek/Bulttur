package com.isanechek.balttur.fragments.web

//import com.izikode.izilib.veinview.defaultClient
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.onClick
import kotlinx.android.synthetic.main.custom_toolbar_layout.*
import kotlinx.android.synthetic.main.web_fragment_layout.*

class WebFragment : BaseFragment() {

    private val url: String
        get() = arguments?.getString("args_url") ?: ""
    private val title: String
        get() = arguments?.getString("args_title") ?: ""

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when {
                web_fragment_wv.canGoBack() -> web_fragment_wv.goBack()
                else -> findNavController().navigateUp()
            }
        }
    }

    override fun getLayout(): Int = _layout.web_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_close_btn.apply {
            visibility = View.VISIBLE
            onClick {
                findNavController().navigateUp()
            }
        }
        toolbar_title.text = title

        web_fragment_wv.apply {
            webChromeClient = TurChromeClient()
            webViewClient = TurClient()
            settings.javaScriptEnabled = true
        }

        web_fragment_wv.loadUrl(url)
    }


    override fun onStart() {
        super.onStart()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }

    inner class TurChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress < 100 && toolbar_progress.visibility == View.GONE) {
                toolbar_progress.visibility = View.VISIBLE
            }
            toolbar_progress.progress = newProgress

            if (newProgress == 100) {
                toolbar_progress.visibility = View.GONE
            }
        }
    }
}