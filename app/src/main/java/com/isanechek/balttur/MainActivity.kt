package com.isanechek.balttur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val controller: NavController by lazy {
        findNavController(_id.main_host_fragment)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onSupportNavigateUp(): Boolean = controller.navigateUp()
}
