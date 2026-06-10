package com.alexdev.minilogbook.data.repository

import com.alexdev.minilogbook.data.local.BgEntryDao
import com.alexdev.minilogbook.data.mapper.toDomainModel
import com.alexdev.minilogbook.data.mapper.toEntry
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.repository.BgRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BgRepositoryImpl @Inject constructor(private val db: BgEntryDao) : BgRepository {

    override suspend fun addBgEntry(entry: BgEntry) = db.insertBgEntry(entry = entry.toEntry())

    override fun getBgEntries(): Flow<List<BgEntry>> = db.getBgEntries().map { entries ->
        entries.map { it.toDomainModel() }
    }
}
