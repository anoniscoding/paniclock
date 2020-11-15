package com.anoniscoding.paniclock.ui.injections

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
private object AppModule {
    @Provides
    @Singleton
    @JvmStatic
    fun providesContext(application: Application):
            Context = application.applicationContext

    @Provides
    @Reusable
    @JvmStatic
    fun provideGson(): Gson = GsonBuilder().create()
}