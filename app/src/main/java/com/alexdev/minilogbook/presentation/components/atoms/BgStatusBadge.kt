package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.domain.model.BgStatus
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun BgStatusBadge(
    modifier: Modifier = Modifier,
    status: BgStatus,
) {
    val style = status.toStyle(isDark = isSystemInDarkTheme())
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .background(color = style.backgroundColor, shape = RoundedCornerShape(percent = 50))
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .semantics { contentDescription = style.label },
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = style.color,
            modifier = Modifier.size(12.dp),
        )
        Text(
            text = style.label,
            color = style.color,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview(showBackground = true, name = "Low")
@Composable
private fun BgStatusBadgePreviewLow() = MiniLogbookTheme { BgStatusBadge(status = BgStatus.LOW) }

@Preview(showBackground = true, name = "Normal")
@Composable
private fun BgStatusBadgePreviewNormal() = MiniLogbookTheme { BgStatusBadge(status = BgStatus.NORMAL) }

@Preview(showBackground = true, name = "Elevated")
@Composable
private fun BgStatusBadgePreviewElevated() = MiniLogbookTheme { BgStatusBadge(status = BgStatus.ELEVATED) }

@Preview(showBackground = true, name = "High")
@Composable
private fun BgStatusBadgePreviewHigh() = MiniLogbookTheme { BgStatusBadge(status = BgStatus.HIGH) }
