package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexdev.minilogbook.core.ui.R
import com.alexdev.minilogbook.domain.model.BgStatus
import com.alexdev.minilogbook.ui.theme.StatusBlue
import com.alexdev.minilogbook.ui.theme.StatusBlueBackground
import com.alexdev.minilogbook.ui.theme.StatusBlueBackgroundDark
import com.alexdev.minilogbook.ui.theme.StatusGreen
import com.alexdev.minilogbook.ui.theme.StatusGreenBackground
import com.alexdev.minilogbook.ui.theme.StatusGreenBackgroundDark
import com.alexdev.minilogbook.ui.theme.StatusOrange
import com.alexdev.minilogbook.ui.theme.StatusOrangeBackground
import com.alexdev.minilogbook.ui.theme.StatusOrangeBackgroundDark
import com.alexdev.minilogbook.ui.theme.StatusRed
import com.alexdev.minilogbook.ui.theme.StatusRedBackground
import com.alexdev.minilogbook.ui.theme.StatusRedBackgroundDark

data class BgStatusStyle(
    val labelRes: Int,
    val color: Color,
    val backgroundColor: Color,
    val icon: ImageVector,
)

fun BgStatus.toStyle(isDark: Boolean = false) =
    when (this) {
        BgStatus.LOW ->
            BgStatusStyle(
                labelRes = R.string.bg_status_low,
                color = StatusBlue,
                backgroundColor = if (isDark) StatusBlueBackgroundDark else StatusBlueBackground,
                icon = Icons.Filled.WaterDrop,
            )

        BgStatus.NORMAL ->
            BgStatusStyle(
                labelRes = R.string.bg_status_normal,
                color = StatusGreen,
                backgroundColor = if (isDark) StatusGreenBackgroundDark else StatusGreenBackground,
                icon = Icons.Filled.Favorite,
            )

        BgStatus.ELEVATED ->
            BgStatusStyle(
                labelRes = R.string.bg_status_elevated,
                color = StatusOrange,
                backgroundColor = if (isDark) StatusOrangeBackgroundDark else StatusOrangeBackground,
                icon = Icons.Filled.WaterDrop,
            )

        BgStatus.HIGH ->
            BgStatusStyle(
                labelRes = R.string.bg_status_high,
                color = StatusRed,
                backgroundColor = if (isDark) StatusRedBackgroundDark else StatusRedBackground,
                icon = Icons.Filled.WaterDrop,
            )
    }
