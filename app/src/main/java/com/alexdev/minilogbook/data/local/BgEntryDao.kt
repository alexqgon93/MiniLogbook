package com.alexdev.minilogbook.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import kotlinx.coroutines.flow.Flow

@Dao
interface BgEntryDao {
    @Query("SELECT * FROM `bg_entry`")
    fun getBgEntries(): Flow<List<BgEntryEntity>>

    @Insert
    suspend fun insertBgEntry(entry: BgEntryEntity)
}