package com.alexdev.minilogbook.presentation.atoms

import com.alexdev.minilogbook.domain.model.BgStatus
import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.atoms.BgStatusBadge
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class BgStatusBadgeSnapshotTest : BaseSnapshotTest() {
    @Test
    fun bgStatusBadgeLow() {
        paparazzi.snapshot { MiniLogbookTheme { BgStatusBadge(status = BgStatus.LOW) } }
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
