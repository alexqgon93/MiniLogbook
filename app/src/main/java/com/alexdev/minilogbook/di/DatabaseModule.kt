package com.alexdev.minilogbook.di

import android.content.Context
import androidx.room.Room
import com.alexdev.minilogbook.data.local.AppDataBase
import com.alexdev.minilogbook.data.local.BgEntryDao
import com.alexdev.minilogbook.data.local.DatabaseMigrations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "minilogbook_database"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDataBase =
        Room
            .databaseBuilder(
                context = context,
                klass = AppDataBase::class.java,
                name = DATABASE_NAME,
            ).addMigrations(*DatabaseMigrations.ALL)
            .build()

    @Provides
    fun providesBgEntryDao(appDatabase: AppDataBase): BgEntryDao = appDatabase.bgEntryDao()
}
