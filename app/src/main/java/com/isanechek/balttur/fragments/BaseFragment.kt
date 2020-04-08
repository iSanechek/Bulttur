package com.isanechek.balttur.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.isanechek.balttur.CLICK_EVENT
import com.isanechek.balttur.actionView
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.utils.DialogUtils
import com.isanechek.balttur.utils.PrefUtils
import com.isanechek.balttur.utils.Tracker
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    val tracker: Tracker by inject()
    val platform: PlatformContract by inject()
    val dialogUtils: DialogUtils by inject()
    val prefUtils: PrefUtils by inject()

    abstract fun getLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(getLayout(), container, false)

    fun clickEvent(msg: String) {
        tracker.event(CLICK_EVENT, msg)
    }

    fun startAction(data: String, tag: String, msg: String) {
        tracker.event(tag, msg)
        requireActivity().actionView { data }
    }

    fun copyDataAction(data: String, tag: String, msg: String) {
        tracker.event(tag, msg)
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Info_data", data)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireActivity(), "Данные скопированы в буфер обмена", Toast.LENGTH_SHORT).show()
    }
}