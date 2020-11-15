package com.anoniscoding.paniclock.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anoniscoding.paniclock.models.CachedVideoInfo
import com.anoniscoding.paniclock.models.CachedVideoInfoConstants
import io.reactivex.Maybe

@Dao
abstract class CachedVideoPathDao {
    @Query(CachedVideoInfoConstants.QUERY_FETCH_VIDEO_INFO)
    abstract fun getVideosInfo(
        offset: Int,
        limit: Int
    ): Maybe<List<CachedVideoInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertVideoInfo(videoInfo: CachedVideoInfo)
}