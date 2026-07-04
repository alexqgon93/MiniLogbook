package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.toBgStatus
import com.alexdev.minilogbook.presentation.components.atoms.BgStatusBadge
import com.alexdev.minilogbook.presentation.components.atoms.toStyle
import com.alexdev.minilogbook.presentation.utils.labelRes
import com.alexdev.minilogbook.ui.theme.Emerald50
import com.alexdev.minilogbook.ui.theme.Emerald500
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun AverageHeader(
    modifier: Modifier = Modifier,
    averageDisplay: String,
    averageMmol: Double?,
    unit: BgUnit,
) {
    val status = averageMmol?.toBgStatus()
    val unitLabel = stringResource(unit.labelRes())
    val statusLabel = status?.let { stringResource(it.toStyle().labelRes) }
    val contentDescriptionText =
        buildString {
            append(stringResource(R.string.your_average_is, averageDisplay, unitLabel))
            if (statusLabel != null) append(", $statusLabel")
        }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {
                    contentDescription = contentDescriptionText
                },
        shape = RoundedCornerShape(size = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        FlowRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                modifier = Modifier.weight(1f),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier =
                        Modifier
                            .size(48.dp)
                            .background(color = Emerald50, shape = RoundedCornerShape(size = 12.dp)),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = null,
                        tint = Emerald500,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Column {
                    Text(
                        text = stringResource(R.string.your_average),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = averageDisplay,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = unitLabel,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                    }
                }
            }
            if (status != null) {
                BgStatusBadge(status = status)
            }
        }
    }
}

@Preview(showBackground = true, name = "mmol/L")
@Composable
private fun AverageHeaderPreviewMmol() =
    MiniLogbookTheme {
        AverageHeader(averageDisplay = "5.50", averageMmol = 5.50, unit = BgUnit.MMOL)
    }

@Preview(showBackground = true, name = "mg/dL")
@Composable
private fun AverageHeaderPreviewMgDl() =
    MiniLogbookTheme {
        AverageHeader(averageDisplay = "99.10", averageMmol = 6.77, unit = BgUnit.MGDL)
    }

@Preview(showBackground = true, name = "Empty state")
@Composable
private fun AverageHeaderPreviewEmpty() =
    MiniLogbookTheme {
        AverageHeader(averageDisplay = "--", averageMmol = null, unit = BgUnit.MMOL)
    }
