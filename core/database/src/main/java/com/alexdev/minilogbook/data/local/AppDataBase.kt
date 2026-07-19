package com.alexdev.minilogbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BgEntryEntity::class], version = 1, exportSchema = true)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bgEntryDao(): BgEntryDao
}
