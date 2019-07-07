package com.isanechek.balttur

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.isanechek.balttur.fragments.license.LicenseFragment
import com.isanechek.balttur.utils.PrefUtils
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.license_fragment_layout.*
import org.koin.android.ext.android.inject

class LicenseActivity : AppCompatActivity() {

    private val pref: PrefUtils by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_layout.license_fragment_layout)

        Picasso.get()
            .load(R.drawable.lic_icon)
            .into(lic_icon_iv)

        license_ok.onClick {
            pref.isLicenseShow = false
            Intent(this, MainActivity::class.java).run {
                startActivity(this)
            }
            finishAfterTransition()
        }

        license_info_tv.apply {
            text = Html.fromHtml(TEXT)
            applyLinks(getLinks())
        }
    }

    private fun getLinks(): List<Link> {

        val ok = Link("Я согласен")
        ok.setTextColor(Color.parseColor("#F4511E"))

        val averd = Link("AverdSoft")
        averd.setTextColor(Color.parseColor("#F4511E"))
        averd.setOnClickListener(object: Link.OnClickListener {
            override fun onClick(clickedText: String) {
                openLink(AVERD_LINK)
            }
        })

        val balttur = Link("БалтТур")
        balttur.setTextColor(Color.parseColor("#F4511E"))
        balttur.setOnClickListener(object: Link.OnClickListener {
            override fun onClick(clickedText: String) {
                openLink(BALTTUR_LINK)
            }
        })
        return listOf(ok, averd, balttur)
    }

    private fun openLink(link: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(link)).run {
            startActivity(this)
        }
    }

    companion object {
        private const val AVERD_LINK = "https://averd-soft.ru/ru/disclaimer.php"
        private const val BALTTUR_LINK = "https://balttur.spb.ru/about-company/"
        private const val TEXT = "<p>Нажав Я согласен, Вы подтверждаете, что прочли и согласны со следующими&nbsp;пользовательскими соглашениями AverdSoft и БалтТур</p>"
    }
}