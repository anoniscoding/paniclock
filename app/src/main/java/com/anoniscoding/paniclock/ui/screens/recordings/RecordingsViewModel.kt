package com.anoniscoding.paniclock.ui.screens.recordings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anoniscoding.paniclock.domain.interactors.GetRecordingsUseCase
import com.anoniscoding.paniclock.models.CachedVideoInfo
import com.anoniscoding.paniclock.ui.screens.common.Resource
import io.reactivex.observers.DisposableObserver

class RecordingsViewModel @ViewModelInject constructor(
    private val _useCase: GetRecordingsUseCase
): ViewModel() {

    private val _recordings = MutableLiveData<Resource<List<CachedVideoInfo>>>()
    val recordings: LiveData<Resource<List<CachedVideoInfo>>> = _recordings

    fun getRecordings(offset: Int, limit: Int) {
        _useCase.execute(
            RecordingsSubscriber(),
            GetRecordingsUseCase.Params.forGetVideos(offset, limit)
        )
    }

    inner class RecordingsSubscriber : DisposableObserver<List<CachedVideoInfo>>() {
        override fun onNext(list: List<CachedVideoInfo>) {
            _recordings.value = Resource.success(list)
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            _recordings.value = Resource.error(e.localizedMessage)
        }
    }
}