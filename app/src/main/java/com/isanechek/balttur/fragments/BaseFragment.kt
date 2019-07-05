package com.isanechek.balttur.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.utils.DialogUtils
import com.isanechek.balttur.utils.Tracker
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    val tracker: Tracker by inject()
    val platform: PlatformContract by inject()
    val dialogUtils: DialogUtils by inject()

    abstract fun getLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(getLayout(), container, false)
}