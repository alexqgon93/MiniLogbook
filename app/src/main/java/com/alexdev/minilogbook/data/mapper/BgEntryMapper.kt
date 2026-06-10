package com.alexdev.minilogbook.data.mapper

import com.alexdev.minilogbook.data.local.BgEntryEntity
import com.alexdev.minilogbook.domain.model.BgEntry

fun BgEntry.toEntry(): BgEntryEntity =
    BgEntryEntity(
        id = id,
        valueInMmol = valueInMmol,
        timestamp = timestamp,
    )

fun BgEntryEntity.toDomainModel(): BgEntry =
    BgEntry(
        id = id,
        valueInMmol = valueInMmol,
        timestamp = timestamp,
    )
