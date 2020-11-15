package com.anoniscoding.paniclock.ui.screens.recordings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.models.CachedVideoInfo
import com.anoniscoding.paniclock.ui.screens.common.Resource
import com.anoniscoding.paniclock.ui.screens.common.ResourceState
import com.anoniscoding.paniclock.ui.screens.extensions.showSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.recordings_fragment.*
import java.io.File


@AndroidEntryPoint
class RecordingsFragment : Fragment(), RecordingAdapter.OnRecordingClick {
    private val _viewModel: RecordingsViewModel by viewModels()
    private val _recordings: MutableList<CachedVideoInfo?> = mutableListOf()
    private var _isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recordings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
        registerObservers()
        initRecordingListAdapter()
        _viewModel.getRecordings(0, 5)
    }

    private fun registerObservers() {
        _viewModel.recordings
            .observe(viewLifecycleOwner, Observer { onRecordingsReceived(it) })
    }

    private fun registerListeners() {
        recording_list.addOnScrollListener(onRecordingsListScroll())
    }

    private fun onRecordingsListScroll(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (shouldLoadMore(linearLayoutManager)) {
                    loadMore()
                    _isLoading = true
                }
            }
        }
    }

    private fun shouldLoadMore(linearLayoutManager: LinearLayoutManager?): Boolean {
        val visibleItemCount = linearLayoutManager?.childCount ?: 0
        val totalItemCount = linearLayoutManager?.itemCount ?: 0
        val firstVisibleItemPosition = linearLayoutManager?.findFirstVisibleItemPosition() ?: 0

        return !_isLoading && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition > 0)
    }

    private fun loadMore() {
        val offset = _recordings.size
        val limit = 5
        _viewModel.getRecordings(offset = offset, limit = limit)
    }

    private fun onRecordingsReceived(it: Resource<List<CachedVideoInfo>>) {
        if (it.status == ResourceState.SUCCESS) {
            it.data?.let { updateRecordingList(it) }
        }

        if (it.status == ResourceState.ERROR) {
            showSnackbar("Error occurred", Snackbar.LENGTH_LONG)
        }
    }

    private fun updateRecordingList(recordings: List<CachedVideoInfo>) {
        this._recordings.addAll(recordings)
        if (recordings.isNotEmpty()) recording_list.adapter?.notifyDataSetChanged()
        _isLoading = false
    }

    override fun onDestroyView() {
        recording_list.adapter = null
        super.onDestroyView()
    }

    private fun initRecordingListAdapter() {
        recording_list.adapter = RecordingAdapter(_recordings, this)
        recording_list.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun viewRecording(filepath: String) {
        val fileUri = getFileUri(filepath)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, "video/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //DO NOT FORGET THIS EVER

        startActivity(intent)
    }

    override fun shareVideoUri(filepath: String) {
        val fileUri = getFileUri(filepath)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, fileUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "video/mp4"
        startActivity(intent)
    }

    private fun getFileUri(filepath: String): Uri? {
        val videoFile = File(filepath)
        val fileUri = FileProvider.getUriForFile(
            requireContext(),
            "com.anoniscoding.paniclock.fileprovider",
            videoFile
        )
        return fileUri
    }
}