package com.alexdev.minilogbook.presentation

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class LogbookSnapshotTest : BaseSnapshotTest() {
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
