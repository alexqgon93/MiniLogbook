package com.alexdev.minilogbook.presentation.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.domain.model.BgStatus
import com.alexdev.minilogbook.presentation.BaseSnapshotTest
import com.alexdev.minilogbook.presentation.components.atoms.BgStatusIcon
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.jupiter.api.Test

class BgStatusIconSnapshotTest : BaseSnapshotTest() {
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
