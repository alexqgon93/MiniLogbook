package com.alexdev.minilogbook.presentation.preview

import com.alexdev.minilogbook.domain.model.BgEntry

internal fun previewEntriesList() =
    listOf(
        BgEntry(id = 1, valueInMmol = 5.5, timestamp = System.currentTimeMillis() - 3_600_000),
        BgEntry(id = 2, valueInMmol = 6.2, timestamp = System.currentTimeMillis() - 7_200_000),
        BgEntry(id = 3, valueInMmol = 4.8, timestamp = System.currentTimeMillis() - 10_800_000),
    )
