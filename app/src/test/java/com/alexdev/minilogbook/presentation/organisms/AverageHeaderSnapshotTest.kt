package com.alexdev.minilogbook.presentation.organisms

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.organisms.AverageHeader
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class AverageHeaderSnapshotTest {
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
    fun averageHeader_emptyState() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                AverageHeader(averageDisplay = "--", averageMmol = null, unit = BgUnit.MMOL)
            }
        }
    }

    @Test
    fun averageHeader_withValue_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                AverageHeader(averageDisplay = "5.50", averageMmol = 5.50, unit = BgUnit.MMOL)
            }
        }
    }

    @Test
    fun averageHeader_withValue_mgdl() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                AverageHeader(averageDisplay = "99.10", averageMmol = 6.77, unit = BgUnit.MGDL)
            }
        }
    }
}
