package com.alexdev.minilogbook.presentation

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo

abstract class BaseSnapshotTest {
    protected lateinit var paparazzi: Paparazzi

    @BeforeEach
    fun setup(testInfo: TestInfo) {
        paparazzi =
            Paparazzi(
                deviceConfig = DeviceConfig.PIXEL_6,
                theme = "Theme.MiniLogbook",
                maxPercentDifference = MAX_PERCENT_DIFFERENCE,
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

    private companion object {
        // Allows for font anti-aliasing differences between macOS and Linux runners;
        // measured cross-OS difference is ~0.014%.
        const val MAX_PERCENT_DIFFERENCE = 0.05
    }
}
