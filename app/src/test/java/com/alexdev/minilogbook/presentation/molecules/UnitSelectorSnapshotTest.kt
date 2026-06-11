package com.alexdev.minilogbook.presentation.molecules

import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.molecules.UnitSelector
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class UnitSelectorSnapshotTest : BaseSnapshotTest() {
    @Test
    fun unitSelector_mmolSelected() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                UnitSelector(selected = BgUnit.MMOL, onUnitSelected = {})
            }
        }
    }

    @Test
    fun unitSelector_mgdlSelected() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                UnitSelector(selected = BgUnit.MGDL, onUnitSelected = {})
            }
        }
    }
}
