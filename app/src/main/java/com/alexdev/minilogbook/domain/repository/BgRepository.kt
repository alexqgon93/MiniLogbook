package com.alexdev.minilogbook.domain.repository

import com.alexdev.minilogbook.domain.model.BgEntry
import kotlinx.coroutines.flow.Flow

interface BgRepository {

    suspend fun addBgEntry(entry: BgEntry)

    fun getBgEntries(): Flow<List<BgEntry>>
}