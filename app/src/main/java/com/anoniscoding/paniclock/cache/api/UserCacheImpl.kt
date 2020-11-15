package com.anoniscoding.paniclock.cache.api

import com.anoniscoding.paniclock.cache.SharedPrefManager
import com.anoniscoding.paniclock.cache.db.AppDatabase
import com.anoniscoding.paniclock.data.user.UserCache
import com.anoniscoding.paniclock.models.CachedVideoInfo
import com.anoniscoding.paniclock.models.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserCacheImpl @Inject constructor(
    private val _pref: SharedPrefManager,
    private val _db: AppDatabase
): UserCache {
    override fun saveUser(user: User) {
        _pref.saveObject(USER_DETAILS, user)
    }

    override fun getUser(): User {
        return _pref.getObject(USER_DETAILS) ?: User.empty()
    }

    override fun saveVideoPath(info: CachedVideoInfo) {
        Single.fromCallable { _db.cachedVideoPathDao().insertVideoInfo(info) }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun getVideos(offset: Int, limit: Int): Observable<List<CachedVideoInfo>> {
        return _db.cachedVideoPathDao().getVideosInfo(offset, limit).toObservable()
    }

    companion object {
        const val USER_DETAILS = "USER_DETAILS"
    }
}