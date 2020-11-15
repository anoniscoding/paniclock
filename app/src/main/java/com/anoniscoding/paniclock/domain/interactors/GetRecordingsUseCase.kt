package com.anoniscoding.paniclock.domain.interactors

import com.anoniscoding.paniclock.domain.base.ObservableUseCase
import com.anoniscoding.paniclock.domain.base.PostExecutionThread
import com.anoniscoding.paniclock.domain.repositories.UserRepository
import com.anoniscoding.paniclock.models.CachedVideoInfo
import io.reactivex.Observable
import javax.inject.Inject

class GetRecordingsUseCase @Inject constructor(
    private val _repository: UserRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<CachedVideoInfo>, GetRecordingsUseCase.Params?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<List<CachedVideoInfo>> {
        requireNotNull(params) { "Params can't be null" }
        return _repository.getVideos(params.offset, params.limit)
    }

    data class Params constructor(val offset: Int, val limit: Int) {
        companion object {
            fun forGetVideos(offset: Int, limit: Int): Params {
                return Params(offset, limit)
            }
        }
    }
}