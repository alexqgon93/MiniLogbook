package com.alexdev.minilogbook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bg_entry")
data class BgEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val valueInMmol: Double,
    val timestamp: Long,
)
