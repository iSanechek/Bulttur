package com.isanechek.balttur.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.isanechek.balttur.R
import com.isanechek.balttur._drawable
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.utils.Tracker
import org.koin.android.ext.android.inject
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback

abstract class BaseFragment : Fragment() {

    val tracker: Tracker by inject()
    val platform: PlatformContract by inject()

    abstract fun getLayout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(getLayout(), container, false)


    fun openTab(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setShowTitle(true)
            .setCloseButtonIcon(getBitmapFromVectorDrawable(_drawable.ic_close_black_24dp, context)!!)
            .build()

        CustomTabsHelper.addKeepAliveExtra(context, builder.intent)
        CustomTabsHelper.openCustomTab(context, builder, Uri.parse(url), WebViewFallback())
    }

    private fun getBitmapFromVectorDrawable(@DrawableRes drawableId: Int, ctx: Context): Bitmap? {
        var drawable = AppCompatResources.getDrawable(ctx, drawableId) ?: return null
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}