package com.anoniscoding.paniclock.ui.injections

import com.anoniscoding.paniclock.domain.base.PostExecutionThread
import com.anoniscoding.paniclock.ui.utils.UiThread
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread
}