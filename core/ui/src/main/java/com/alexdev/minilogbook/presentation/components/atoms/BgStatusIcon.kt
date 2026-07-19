package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.domain.model.BgStatus
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun BgStatusIcon(
    modifier: Modifier = Modifier,
    status: BgStatus,
) {
    val style = status.toStyle(isDark = isSystemInDarkTheme())
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .size(40.dp)
                .background(
                    color = style.backgroundColor,
                    shape = RoundedCornerShape(size = 12.dp),
                ),
    ) {
        Icon(
            imageVector = style.icon,
            contentDescription = null,
            tint = style.color,
            modifier = Modifier.size(size = 12.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BgStatusIconPreview() =
    MiniLogbookTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BgStatus.entries.forEach { BgStatusIcon(status = it) }
        }
    }
