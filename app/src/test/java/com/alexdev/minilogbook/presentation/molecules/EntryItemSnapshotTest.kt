package com.alexdev.minilogbook.presentation.molecules

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.molecules.EntryItem
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class EntryItemSnapshotTest {

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
    fun entryItem_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                EntryItem(
                    entry = BgEntry(id = 1, valueInMmol = 5.5, timestamp = 0L),
                    unit = BgUnit.MMOL
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
                    unit = BgUnit.MGDL
                )
            }
        }
    }
}
