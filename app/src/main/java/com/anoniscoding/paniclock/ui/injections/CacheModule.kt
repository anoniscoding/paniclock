package com.anoniscoding.paniclock.ui.injections

import android.app.Application
import com.anoniscoding.paniclock.cache.api.UserCacheImpl
import com.anoniscoding.paniclock.cache.db.AppDatabase
import com.anoniscoding.paniclock.data.user.UserCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class CacheModule {
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): AppDatabase {
            return AppDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindUserCache(cache: UserCacheImpl): UserCache
}