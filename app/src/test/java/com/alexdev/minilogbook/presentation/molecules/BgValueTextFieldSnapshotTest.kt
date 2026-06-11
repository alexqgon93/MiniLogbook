package com.alexdev.minilogbook.presentation.molecules

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.presentation.components.molecules.BgValueTextField
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class BgValueTextFieldSnapshotTest {

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
    fun bgValueTextField_idle_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "5.5",
                    unitLabel = "mmol/L",
                    isError = false,
                    onValueChange = {}
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
                    onValueChange = {}
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
                    onValueChange = {}
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
                    onValueChange = {}
                )
            }
        }
    }
}
