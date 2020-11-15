package com.anoniscoding.paniclock.domain.repositories

import com.anoniscoding.paniclock.models.CachedVideoInfo
import com.anoniscoding.paniclock.models.User
import io.reactivex.Observable

interface UserRepository {
    fun getUserDetails(): User
    fun updateUserDetails(user: User)
    fun saveVideoPath(videoInfo: CachedVideoInfo)
    fun getVideos(offset: Int, limit: Int): Observable<List<CachedVideoInfo>>
}