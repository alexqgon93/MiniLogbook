package com.alexdev.minilogbook.presentation

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class LogbookSnapshotTest {
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

    private fun previewEntries() =
        listOf(
            BgEntry(id = 1, valueInMmol = 5.5, timestamp = 0L),
            BgEntry(id = 2, valueInMmol = 6.2, timestamp = 0L),
            BgEntry(id = 3, valueInMmol = 4.8, timestamp = 0L),
        )

    @Test
    fun logbook_emptyState() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }
    }

    @Test
    fun logbook_withEntries_mmol() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            selectedUnit = BgUnit.MMOL,
                            inputValue = "5.5",
                            averageDisplay = "5.50",
                            entries = previewEntries(),
                        ),
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun logbook_withEntries_mgdl() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            selectedUnit = BgUnit.MGDL,
                            inputValue = "99.10",
                            averageDisplay = "122.00",
                            entries = previewEntries(),
                        ),
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun logbook_inputErrorState() {
        paparazzi.snapshot {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            inputValue = "-1",
                            inputError = "Must be >= 2.8 mmol/L",
                        ),
                    onEvent = {},
                )
            }
        }
    }
}
