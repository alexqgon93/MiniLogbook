package com.alexdev.minilogbook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BgEntryDao {
    @Query("SELECT * FROM `bg_entry` ORDER BY timestamp DESC")
    fun getBgEntries(): Flow<List<BgEntryEntity>>

    @Insert
    suspend fun insertBgEntry(entry: BgEntryEntity)
}
