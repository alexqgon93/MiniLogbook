package com.alexdev.minilogbook.presentation.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgStatus
import com.alexdev.minilogbook.presentation.components.atoms.BgStatusIcon
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class BgStatusIconSnapshotTest {
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
    fun bgStatusIcon_all_in_one_row() {
        paparazzi.snapshot(name = "bgStatusIcon_all") {
            MiniLogbookTheme {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BgStatus.entries.forEach { status ->
                        BgStatusIcon(status = status)
                    }
                }
            }
        }
    }
}
