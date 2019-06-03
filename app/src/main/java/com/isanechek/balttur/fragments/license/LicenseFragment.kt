package com.isanechek.balttur.fragments.license

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.navigation.fragment.findNavController
import com.isanechek.balttur.R
import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.onClick
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import kotlinx.android.synthetic.main.license_fragment_layout.*

class LicenseFragment : BaseFragment() {



    override fun getLayout(): Int = _layout.license_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        license_ok.onClick {
            platform.licenseMarkNotShow()
            findNavController().navigateUp()
        }

        license_info_tv.apply {
            text = Html.fromHtml(TEXT)
            applyLinks(getLinks())
        }
    }


    private fun getLinks(): List<Link> {

        val ok = Link("Я согласен")
        ok.setTextColor(R.color.colorAccent)

        val averd = Link("AverdSoft")
        averd.setTextColor(R.color.colorAccent)
        averd.setOnClickListener(object: Link.OnClickListener {
            override fun onClick(clickedText: String) {
                openLink(AVERD_LINK)
            }
        })

        val balttur = Link("БалтТур")
        balttur.setTextColor(R.color.colorAccent)
        balttur.setOnClickListener(object: Link.OnClickListener {
            override fun onClick(clickedText: String) {
                openLink(BALTTUR_LINK)
            }
        })
        return listOf(ok, averd, balttur)
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }


    companion object {
        private const val AVERD_LINK = "https://averd-soft.ru/ru/disclaimer.php"
        private const val BALTTUR_LINK = "https://balttur.spb.ru/about-company/"
        private const val TEXT = "<p>Нажав Я согласен, Вы подтверждаете, что прочли и согласны со следующими&nbsp;пользовательскими соглашениями AverdSoft и БалтТур</p>"
    }

}