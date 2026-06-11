package com.alexdev.minilogbook.presentation.organisms

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.presentation.components.organisms.BgInputRow
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class BgInputRowSnapshotTest {

    private lateinit var paparazzi: Paparazzi

    @BeforeEach
    fun setup(testInfo: TestInfo) {
        paparazzi = Paparazzi(
            deviceConfig = DeviceConfig.PIXEL_6,
            theme = "Theme.MiniLogbook"
        ).apply {
            setup(
                testName = TestName(
                    packageName = testInfo.testClass.get().`package`?.name.orEmpty(),
                    className = testInfo.testClass.get().simpleName,
                    methodName = testInfo.testMethod.get().name
                )
            )
        }
    }

    @AfterEach
    fun tearDown() {
        paparazzi.teardown()
    }

    @Test
    fun bgInputRow_emptyInput_saveDisabled() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgInputRow(
                    value = "",
                    unitLabel = "mmol/L",
                    error = null,
                    onValueChange = {},
                    onSave = {}
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
                    onSave = {}
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
                    onSave = {}
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
                    onSave = {}
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
                    onSave = {}
                )
            }
        }
    }
}
