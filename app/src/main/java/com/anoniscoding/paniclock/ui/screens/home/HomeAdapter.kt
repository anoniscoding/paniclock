package com.anoniscoding.paniclock.ui.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anoniscoding.paniclock.R
import kotlinx.android.synthetic.main.nav_item_view.view.*

class HomeAdapter(
    private val _navClickListener: OnNavClickListener
) : RecyclerView.Adapter<HomeAdapter.NavViewHolder>() {
    private val _homeItems: List<String> = listOf("Recordings", "Change lock codes")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.nav_item_view, parent, false)
        return NavViewHolder(view)
    }

    override fun getItemCount(): Int = _homeItems.size

    override fun onBindViewHolder(viewHolder: NavViewHolder, position: Int) {
        viewHolder.setViewData(position)
    }

    inner class NavViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                _navClickListener.onNavItemClick(absoluteAdapterPosition)
            }
        }

        fun setViewData(position: Int) {
            when(position) {
                RECORDING_POSTION -> {
                    view.nav_title.text = _homeItems.elementAt(0)
                    view.image.setImageResource(R.drawable.ic_media_library)
                }
                CHANGE_LOCK_POSITION -> {
                    view.nav_title.text = _homeItems.elementAt(1)
                    view.image.setImageResource(R.drawable.ic_shield_icon)
                }
            }
        }
    }

    interface OnNavClickListener {
        fun onNavItemClick(position: Int)
    }

    companion object {
        const val RECORDING_POSTION = 0
        const val CHANGE_LOCK_POSITION = 1
    }
}