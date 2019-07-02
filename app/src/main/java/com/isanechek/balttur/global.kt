package com.isanechek.balttur

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.isanechek.balttur.utils.KCallback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

typealias _layout = R.layout
typealias _id = R.id
typealias _drawable = R.drawable
typealias _color = R.color
typealias _anim = R.anim

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun View.onClick(function: () -> Unit) {
    setOnClickListener {
        function()
    }
}

const val BASE_URL = "https://balttur.spb.ru"

fun View.hideWithAlpha(time: Long) {

    AlphaAnimation(1f, 0f)
        .run {
            duration = time
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    visibility = View.INVISIBLE
                }
            })
            startAnimation(this)
        }
}

fun View.showWithAlpha(time: Long) {
    AlphaAnimation(0f, 1f)
        .run {
            duration = time
            fillAfter = true
            visibility = View.VISIBLE
            startAnimation(this)
        }
}

fun ImageView.generatePalette(listener: (Palette?) -> Unit) {
    Palette.from((this.drawable as BitmapDrawable).bitmap).generate(listener)
}

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}

inline fun ImageView.loadUrl(url: String, callback: KCallback.() -> Unit) {
    Picasso.get().load(url).intoWithCallback(this, callback)
}

inline fun RequestCreator.intoWithCallback(target: ImageView, callback: KCallback.() -> Unit) {
    this.into(target, KCallback().apply(callback))
}



object CustomShape {
    fun bindBg(view: View, color: Int) {
        val shape = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(
            color,
            ColorUtils.setAlphaComponent(color, 100)))
        view.background = shape
    }
}


val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

const val DIGNITY_DATA = "Туроператор с 2001 года.Член Российского Союза Туриндустрии и ассоциации «Турпомощь».Все программы являются авторскими, теперь и с авиаперелетом.Работаем со всеми регионами Российской Федерации.Расширенная география путешествий по Европе.Единственный туроператор в СПб по автобусным турам в Турцию.Работаем только с надежными и проверенными партнерами.Мы постоянно повышаем качество разработанных нами туров.Отсутствие ночных переездов.Самые интересные сочетания городов и стран.Возможность комбинировать наши туры: автобус + ж/д, автобус + авиа.Самые подробные описания маршрутов.Профессиональные гиды.Индивидуальный подход к каждому туристу.Возможность выбрать и бесплатно зафиксировать место в комфортабельном автобусе,Организуем любой тур по Вашему запросу,Бесплатно найдем Вам попутчика,Наши туры интересны туристам любой возрастной категории.Скидки для постоянных клиентов и групп от 3-х человек.Для организованных групп действует система 10 + 1 бесплатный.Дополнительные скидки детям до 15 лет.Имеем аккредитацию во всех Консульствах стран Шенгенского оглашения"