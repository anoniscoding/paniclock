package com.anoniscoding.paniclock.ui.screens.recordings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.models.CachedVideoInfo
import kotlinx.android.synthetic.main.recording_view.view.*

class RecordingAdapter(
    private val _cachedVideoInfo: List<CachedVideoInfo?>,
    private val _onClickListener: OnRecordingClick
): RecyclerView.Adapter<RecordingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recording_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _cachedVideoInfo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cachedVideoInfo = _cachedVideoInfo.elementAt(position)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var cachedVideoInfo: CachedVideoInfo? = null
            set(value) {
                field = value
                view.location.text = "Timestamp: ${value?.timestamp}"+ "\n" + value?.latlng
            }

        init {
            view.setOnClickListener {
                cachedVideoInfo?.let {
                    _onClickListener.viewRecording(it.path)
                }
            }
            view.share_buttion.setOnClickListener {
                cachedVideoInfo?.let {
                    _onClickListener.shareVideoUri(it.path)
                }
            }
        }
    }

    interface OnRecordingClick {
        fun viewRecording(filepath: String)
        fun shareVideoUri(filepath: String)
    }
}