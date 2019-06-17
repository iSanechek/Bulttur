package com.isanechek.balttur.debug

import com.isanechek.balttur._layout
import com.isanechek.balttur.fragments.BaseFragment

class DebugFragment : BaseFragment() {

    override fun getLayout(): Int {
        return _layout.debug_fragment_layout
    }

}