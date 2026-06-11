package com.alexdev.minilogbook.presentation.molecules

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.molecules.EntryItem
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class EntryItemSnapshotTest : BaseSnapshotTest() {
    @Test
    fun entryItem_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                EntryItem(
                    entry = BgEntry(id = 1, valueInMmol = 5.5, timestamp = 0L),
                    unit = BgUnit.MMOL,
                )
            }
        }
    }

    @Test
    fun entryItem_mgdl() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                EntryItem(
                    entry = BgEntry(id = 1, valueInMmol = 5.5, timestamp = 0L),
                    unit = BgUnit.MGDL,
                )
            }
        }
    }
}
