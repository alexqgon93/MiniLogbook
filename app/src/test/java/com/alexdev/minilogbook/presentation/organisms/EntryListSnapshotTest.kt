package com.alexdev.minilogbook.presentation.organisms

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.organisms.EntryList
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class EntryListSnapshotTest : BaseSnapshotTest() {
    private fun previewEntries() =
        listOf(
            BgEntry(id = 1, valueInMmol = 5.5, timestamp = 0L),
            BgEntry(id = 2, valueInMmol = 6.2, timestamp = 0L),
            BgEntry(id = 3, valueInMmol = 4.8, timestamp = 0L),
        )

    @Test
    fun entryList_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                EntryList(entries = previewEntries(), unit = BgUnit.MMOL)
            }
        }
    }

    @Test
    fun entryList_mgdl() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                EntryList(entries = previewEntries(), unit = BgUnit.MGDL)
            }
        }
    }
}
