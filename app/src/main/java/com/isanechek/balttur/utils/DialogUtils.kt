package com.isanechek.balttur.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

interface DialogUtils {
    fun showWarningBrowserDialog(context: Context, callback: (Boolean) -> Unit)
}

class DialogUtilsImpl(private val pref: PrefUtils) : DialogUtils {

    override fun showWarningBrowserDialog(context: Context, callback: (Boolean) -> Unit) {
        when {
            pref.isWarningBrowserShow -> MaterialDialog(context).show {
                title(text = "Предупреждение")
                message(text = "Ввиду технических ограничений Вы будите перенаправлены в браузер")
                positiveButton(text = "Ok") {
                    pref.isWarningBrowserShow = false
                    callback(true)
                    it.dismiss()
                }
                negativeButton(text = "Отменить") {
                    pref.isWarningBrowserShow = false
                    callback(false)
                    it.dismiss()
                }
            }
            else -> callback(true)
        }
    }
}