package com.alexdev.minilogbook.presentation.organisms

import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.organisms.BgInputRow
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class BgInputRowSnapshotTest : BaseSnapshotTest() {
    @Test
    fun bgInputRow_emptyInput_saveDisabled() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgInputRow(
                    value = "",
                    unitLabel = "mmol/L",
                    error = null,
                    onValueChange = {},
                    onSave = {},
                )
            }
        }
    }

    @Test
    fun bgInputRow_validInput_saveEnabled() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgInputRow(
                    value = "5.5",
                    unitLabel = "mmol/L",
                    error = null,
                    onValueChange = {},
                    onSave = {},
                )
            }
        }
    }

    @Test
    fun bgInputRow_errorState_tooLow() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgInputRow(
                    value = "1.0",
                    unitLabel = "mmol/L",
                    error = "Must be >= 2.8 mmol/L",
                    onValueChange = {},
                    onSave = {},
                )
            }
        }
    }

    @Test
    fun bgInputRow_errorState_tooHigh() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgInputRow(
                    value = "999.0",
                    unitLabel = "mmol/L",
                    error = "Must be <= 33.3 mmol/L",
                    onValueChange = {},
                    onSave = {},
                )
            }
        }
    }

    @Test
    fun bgInputRow_mgdl_validInput() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgInputRow(
                    value = "99.10",
                    unitLabel = "mg/dL",
                    error = null,
                    onValueChange = {},
                    onSave = {},
                )
            }
        }
    }
}
