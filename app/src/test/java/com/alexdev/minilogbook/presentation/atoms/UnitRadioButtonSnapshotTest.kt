package com.alexdev.minilogbook.presentation.atoms

import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.atoms.UnitRadioButton
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class UnitRadioButtonSnapshotTest : BaseSnapshotTest() {
    @Test
    fun unitRadioButton_mmol_selected() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MMOL, selected = true, onSelected = {})
            }
        }
    }

    @Test
    fun unitRadioButton_mmol_unselected() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MMOL, selected = false, onSelected = {})
            }
        }
    }

    @Test
    fun unitRadioButton_mgdl_selected() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MGDL, selected = true, onSelected = {})
            }
        }
    }

    @Test
    fun unitRadioButton_mgdl_unselected() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MGDL, selected = false, onSelected = {})
            }
        }
    }
}
