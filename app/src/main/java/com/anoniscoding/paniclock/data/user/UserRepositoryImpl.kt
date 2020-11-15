package com.anoniscoding.paniclock.data.user

import com.anoniscoding.paniclock.models.User
import com.anoniscoding.paniclock.domain.repositories.UserRepository
import com.anoniscoding.paniclock.models.CachedVideoInfo
import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val _cache: UserCache
): UserRepository {

    override fun getUserDetails(): User {
        return _cache.getUser()
    }

    override fun updateUserDetails(user: User) {
        _cache.saveUser(user)
    }

    override fun saveVideoPath(videoInfo: CachedVideoInfo) {
        _cache.saveVideoPath(videoInfo)
    }

    override fun getVideos(offset: Int, limit: Int): Observable<List<CachedVideoInfo>> {
        return _cache.getVideos(offset, limit)
    }
}