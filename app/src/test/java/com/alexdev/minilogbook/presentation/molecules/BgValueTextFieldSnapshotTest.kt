package com.alexdev.minilogbook.presentation.molecules

import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.molecules.BgValueTextField
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class BgValueTextFieldSnapshotTest : BaseSnapshotTest() {
    @Test
    fun bgValueTextField_idle_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "5.5",
                    unitLabel = "mmol/L",
                    isError = false,
                    onValueChange = {},
                )
            }
        }
    }

    @Test
    fun bgValueTextField_idle_mgdl() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "99.10",
                    unitLabel = "mg/dL",
                    isError = false,
                    onValueChange = {},
                )
            }
        }
    }

    @Test
    fun bgValueTextField_empty() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "",
                    unitLabel = "mmol/L",
                    isError = false,
                    onValueChange = {},
                )
            }
        }
    }

    @Test
    fun bgValueTextField_errorState() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "-1",
                    unitLabel = "mmol/L",
                    isError = true,
                    onValueChange = {},
                )
            }
        }
    }
}
