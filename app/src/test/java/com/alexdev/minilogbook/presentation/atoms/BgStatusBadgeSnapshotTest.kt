package com.alexdev.minilogbook.presentation.atoms

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgStatus
import com.alexdev.minilogbook.presentation.components.atoms.BgStatusBadge
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class BgStatusBadgeSnapshotTest {
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
    fun bgStatusBadgeLow() {
        paparazzi.snapshot {
            MiniLogbookTheme { BgStatusBadge(status = BgStatus.LOW) }
        }
    }

    @Test
    fun bgStatusBadgeNormal() {
        paparazzi.snapshot { MiniLogbookTheme { BgStatusBadge(status = BgStatus.NORMAL) } }
    }

    @Test
    fun bgStatusBadgeElevated() {
        paparazzi.snapshot { MiniLogbookTheme { BgStatusBadge(status = BgStatus.ELEVATED) } }
    }

    @Test
    fun bgStatusBadgeHigh() {
        paparazzi.snapshot { MiniLogbookTheme { BgStatusBadge(status = BgStatus.HIGH) } }
    }
}
