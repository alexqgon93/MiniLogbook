package com.alexdev.minilogbook.di

import com.alexdev.minilogbook.data.time.SystemTimeProvider
import com.alexdev.minilogbook.domain.time.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeModule {
    @Binds
    abstract fun bindTimeProvider(systemTimeProvider: SystemTimeProvider): TimeProvider
}
