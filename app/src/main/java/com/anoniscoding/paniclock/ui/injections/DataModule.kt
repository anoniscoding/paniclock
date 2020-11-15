package com.anoniscoding.paniclock.ui.injections

import com.anoniscoding.paniclock.data.user.UserRepositoryImpl
import com.anoniscoding.paniclock.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}