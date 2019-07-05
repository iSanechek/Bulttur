package com.isanechek.balttur.fragments.connect

import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.astritveliu.boom.Boom
import com.isanechek.balttur._layout
import com.isanechek.balttur.inflate
import com.isanechek.balttur.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.connect_header_free_layout.*
import kotlinx.android.synthetic.main.connect_list_item_layout.*

class ConnectAdapter(private val callback: (Pair<String,String>) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = listOf(
        Pair("header_free", ""),
        Pair("free_tel", "88003503972"),
        Pair("second_header", ""),
        Pair("tel", "+79219597602"),
        Pair("tel", "+78123141553"),
        Pair("tel", "+78123141621"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            FREE_HEADER_TYPE -> HeaderHolder(parent.inflate(_layout.connect_header_free_layout), true)
            HEADER_TYPE -> HeaderHolder(parent.inflate(_layout.connect_header_free_layout), false)
            FREE_ITEM_LINE_TYPE -> ItemHolder(parent.inflate(_layout.connect_list_item_layout))
            ITEM_TYPE -> ItemHolder(parent.inflate(_layout.connect_list_item_layout))
            else -> HeaderHolder(parent.inflate(_layout.connect_header_free_layout), true)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderHolder) {
            holder.bindTo()
        } else if (holder is ItemHolder) {
            holder.bindTo(items[position], callback)
        }
    }

    override fun getItemViewType(position: Int): Int = when(items[position].first) {
        "header_free" -> FREE_HEADER_TYPE
        "free_tel" -> FREE_ITEM_LINE_TYPE
        "second_header" -> HEADER_TYPE
        "tel" -> ITEM_TYPE
        else -> ITEM_TYPE
    }

    inner class HeaderHolder(override val containerView: View, private val isFree: Boolean) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindTo() {
            when {
                isFree -> connect_header_title.apply {
                    text = "Бесплатная линия"
                    setTextColor("#2AB16A".toColorInt())
                }
                else -> connect_header_title.apply {
                    text = "С-Петербург"
                    setTextColor("#FFFFFF".toColorInt())
                }
            }
        }
    }

    inner class ItemHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindTo(item: Pair<String, String>, callback: (Pair<String, String>) -> Unit) {
            connect_item_call_number.text = item.second
            connect_item_container.apply {
                Boom(this)
                onClick {
                    callback(item)
                }
                setOnLongClickListener {
                    callback(Pair("long", item.second))
                    true
                }
            }
        }
    }

    companion object {
        private const val FREE_HEADER_TYPE = 0
        private const val FREE_ITEM_LINE_TYPE = 4
        private const val ITEM_TYPE = 1
        private const val HEADER_TYPE = 2
    }
}