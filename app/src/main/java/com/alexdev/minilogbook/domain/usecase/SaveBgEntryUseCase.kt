package com.alexdev.minilogbook.domain.usecase

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.repository.BgRepository
import javax.inject.Inject

class SaveBgEntryUseCase
    @Inject
    constructor(
        private val repository: BgRepository,
    ) {
        suspend operator fun invoke(entry: BgEntry) = repository.addBgEntry(entry)
    }
