package com.alexdev.minilogbook.presentation.organisms

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.organisms.EntryList
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import kotlin.collections.emptyList

class EntryListSnapshotTest {

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

    private fun previewEntries() = listOf(
        BgEntry(id = 1, valueInMmol = 5.5, timestamp = 0L),
        BgEntry(id = 2, valueInMmol = 6.2, timestamp = 0L),
        BgEntry(id = 3, valueInMmol = 4.8, timestamp = 0L),
    )

    @Test
    fun entryList_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                EntryList(entries = previewEntries(), unit = BgUnit.MMOL)
            }
        }
    }

    @Test
    fun entryList_mgdl() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                EntryList(entries = previewEntries(), unit = BgUnit.MGDL)
            }
        }
    }
}
