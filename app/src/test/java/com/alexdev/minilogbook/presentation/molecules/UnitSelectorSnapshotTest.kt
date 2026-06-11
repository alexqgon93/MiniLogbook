package com.alexdev.minilogbook.presentation.molecules

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.molecules.UnitSelector
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class UnitSelectorSnapshotTest {
    private lateinit var paparazzi: Paparazzi

    @BeforeEach
    fun setup(testInfo: TestInfo) {
        paparazzi =
            Paparazzi(
                deviceConfig = DeviceConfig.PIXEL_6,
                theme = "Theme.MiniLogbook",
            ).apply {
                setup(
                    testName =
                        TestName(
                            packageName =
                                testInfo.testClass
                                    .get()
                                    .`package`
                                    ?.name
                                    .orEmpty(),
                            className = testInfo.testClass.get().simpleName,
                            methodName = testInfo.testMethod.get().name,
                        ),
                )
            }
    }

    @AfterEach
    fun tearDown() {
        paparazzi.teardown()
    }

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
