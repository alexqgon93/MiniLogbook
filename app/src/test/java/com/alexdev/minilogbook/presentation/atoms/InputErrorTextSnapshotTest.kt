package com.alexdev.minilogbook.presentation.atoms

import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.atoms.InputErrorText
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class InputErrorTextSnapshotTest : BaseSnapshotTest() {
    @Test
    fun inputErrorText_visible() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                InputErrorText(error = "Must be >= 2.8 mmol/L")
            }
        }
    }
}
