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
typealias _style = R.style

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

const val DEV_WEB_SITE = "https://averd-soft.ru"
const val CONSULT_ONLINE_URL = "https://quicksale-chat.consultsystems.ru/?site_hash=hIjqC4wVW07T&amp;visitor_hash=xeUg7L9pTcvm&amp;page_title=%D0%9F%D0%BE%D0%B8%D1%81%D0%BA+%D1%82%D1%83%D1%80%D0%BE%D0%B2&amp;page_url=https%3A%2F%2Fbalttur.spb.ru%2Ffilter%2F&amp;department_id=0&amp;mobile=0&amp;editor_mode=0&amp;rnd=4347"
const val DIGNITY_DATA = "Туроператор с 2001 года.Член Российского Союза Туриндустрии и ассоциации «Турпомощь».Все программы являются авторскими, теперь и с авиаперелетом.Работаем со всеми регионами Российской Федерации.Расширенная география путешествий по Европе.Единственный туроператор в СПб по автобусным турам в Турцию.Работаем только с надежными и проверенными партнерами.Мы постоянно повышаем качество разработанных нами туров.Отсутствие ночных переездов.Самые интересные сочетания городов и стран.Возможность комбинировать наши туры: автобус + ж/д, автобус + авиа.Самые подробные описания маршрутов.Профессиональные гиды.Индивидуальный подход к каждому туристу.Возможность выбрать и бесплатно зафиксировать место в комфортабельном автобусе,Организуем любой тур по Вашему запросу,Бесплатно найдем Вам попутчика,Наши туры интересны туристам любой возрастной категории.Скидки для постоянных клиентов и групп от 3-х человек.Для организованных групп действует система 10 + 1 бесплатный.Дополнительные скидки детям до 15 лет.Имеем аккредитацию во всех Консульствах стран Шенгенского оглашения"