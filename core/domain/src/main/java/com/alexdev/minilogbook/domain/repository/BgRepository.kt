package com.alexdev.minilogbook.domain.repository

import com.alexdev.minilogbook.domain.model.BgEntry
import kotlinx.coroutines.flow.Flow

interface BgRepository {
    suspend fun addBgEntry(entry: BgEntry)

    fun getBgEntries(limit: Int = DEFAULT_HISTORY_LIMIT): Flow<List<BgEntry>>

    companion object {
        const val DEFAULT_HISTORY_LIMIT = 500
    }
}
