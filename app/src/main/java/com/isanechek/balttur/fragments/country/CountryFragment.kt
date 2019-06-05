package com.isanechek.balttur.fragments.country

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.isanechek.balttur._layout
import com.isanechek.balttur.data.models.Resource
import com.isanechek.balttur.data.models.Status
import com.isanechek.balttur.fragments.BaseFragment
import com.isanechek.balttur.fragments.BaseListFragment
import kotlinx.android.synthetic.main.base_list_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryFragment : BaseListFragment() {

    private val vm: CountryViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tracker.event(TAG, "Open Country Screen")

        val countryAdapter = CountryAdapter { callback ->

        }

        with(base_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }


        vm.loadData().observe(this, Observer { resource ->
            if (resource.data != null) {
                countryAdapter.submit(resource.data)
            }

            when(resource.status) {
                Status.LOADING -> {
                    tracker.event(TAG, "Loading")
                }
                Status.ERROR -> {
                    tracker.event(TAG, "Error ${resource.message}")
                }
                Status.SUCCESS -> {
                    tracker.event(TAG, "Success ${resource.data?.size}")
                }
            }
        })

    }

    companion object {
        private const val TAG = "CountryFragment"
    }

}