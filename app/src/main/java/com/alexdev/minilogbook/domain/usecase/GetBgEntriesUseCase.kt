package com.alexdev.minilogbook.domain.usecase

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.repository.BgRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBgEntriesUseCase @Inject constructor(private val repository: BgRepository) {
    operator fun invoke(): Flow<List<BgEntry>> = repository.getBgEntries()
}