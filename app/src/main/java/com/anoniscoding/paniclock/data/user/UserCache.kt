package com.anoniscoding.paniclock.data.user

import com.anoniscoding.paniclock.models.CachedVideoInfo
import com.anoniscoding.paniclock.models.User
import io.reactivex.Observable

interface UserCache {
    fun saveUser(user: User)
    fun getUser(): User
    fun saveVideoPath(info: CachedVideoInfo)
    fun getVideos(offset: Int, limit: Int): Observable<List<CachedVideoInfo>>
}