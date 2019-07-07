package com.isanechek.balttur.fragments.appinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.astritveliu.boom.Boom
import com.isanechek.balttur.*
import com.isanechek.balttur.fragments.BaseFragment
import kotlinx.android.synthetic.main.custom_toolbar_layout.*
import kotlinx.android.synthetic.main.developer_info_fragment_layout.*

class DeveloperInfoFragment : BaseFragment() {

    override fun getLayout(): Int = _layout.developer_info_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tracker.event(OPEN_SCREEN_TAG, "Открыт экран о разработчиках")
        toolbar_close_btn.apply {
            visibility = View.VISIBLE
            onClick {
                findNavController().navigateUp()
            }
        }
        toolbar_title.text = "Разработчики"

        dev_write_email_btn.apply {
            Boom(this)
            onClick {
                MaterialDialog(requireContext()).show {
                    lifecycleOwner(this@DeveloperInfoFragment)
                    listItems(
                        items = listOf(
                            "averdsoft@mail.ru",
                            "averdsoft@gmail.com"
                        )
                    ) { dialog, _, email ->
                        requireActivity().sendEmail("Балтур", email, "")
                        dialog.dismiss()
                    }
                    negativeButton(text = "Закрыть") {
                        it.dismiss()
                    }
                }
            }
        }

        dev_gp_btn.apply {
            handleAction(this, "https://play.google.com/store/apps/dev?id=6812241770877419123")
        }
        dev_vk_btn.apply {
            handleAction(this, "https://vk.com/averdsoft")
        }

        dev_ok_btn.apply {
            handleAction(this, "https://ok.ru/averdsoft")
        }

        dev_insta_btn.apply {
            handleAction(this, "https://instagram.com/averdsoft")
        }

        dev_telegram_btn.apply {
            handleAction(this, "https://t.me/averdsoft")
        }

        dev_twitter_btn.apply {
            handleAction(this, "https://twitter.com/averdsoft")
        }

        dev_facebook_btn.apply {
            handleAction(this, "https://www.facebook.com/averdsoft")
        }

        dev_web_btn.apply {
            handleAction(this, DEV_WEB_SITE)
        }
    }

    private fun handleAction(view: View, data: String) {
        Boom(view)
        view.onClick {
            requireActivity().actionView { data }
        }
    }
}