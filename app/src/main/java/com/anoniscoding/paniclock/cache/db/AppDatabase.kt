package com.anoniscoding.paniclock.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anoniscoding.paniclock.cache.dao.CachedVideoPathDao
import com.anoniscoding.paniclock.models.CachedVideoInfo

@Database(entities = [CachedVideoInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun cachedVideoPathDao(): CachedVideoPathDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, DB_NAME)
                            .build()
                    }
                    return INSTANCE as AppDatabase
                }
            }
            return INSTANCE as AppDatabase
        }


        private const val DB_NAME = "paniclock.db"
    }

}