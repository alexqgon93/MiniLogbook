package com.alexdev.minilogbook.presentation.atoms

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.atoms.UnitRadioButton
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class UnitRadioButtonSnapshotTest {

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
