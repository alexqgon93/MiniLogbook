package com.alexdev.minilogbook.presentation.organisms

import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.organisms.AverageHeader
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class AverageHeaderSnapshotTest : BaseSnapshotTest() {
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
