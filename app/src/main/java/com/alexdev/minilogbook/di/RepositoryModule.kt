package com.alexdev.minilogbook.di

import com.alexdev.minilogbook.data.repository.BgRepositoryImpl
import com.alexdev.minilogbook.domain.repository.BgRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(bgRepositoryImpl: BgRepositoryImpl): BgRepository
}
