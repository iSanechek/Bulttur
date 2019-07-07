package com.isanechek.balttur

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.isanechek.balttur.utils.PrefUtils
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val pref: PrefUtils by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (pref.isLicenseShow) {
            Intent(this, LicenseActivity::class.java).run {
                startActivity(this)
            }
            finishAfterTransition()
        } else {
            Intent(this, MainActivity::class.java).run {
                startActivity(this)
            }
            finishAfterTransition()
        }
    }
}