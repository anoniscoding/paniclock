package com.anoniscoding.paniclock.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CachedVideoInfoConstants.TABLE_NAME)
data class CachedVideoInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var path: String,
    var timestamp: String,
    var latlng: String
)

object CachedVideoInfoConstants {
    const val TABLE_NAME = "cachedvideoinfo"
    const val QUERY_FETCH_VIDEO_INFO = "SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT :limit OFFSET :offset"
}