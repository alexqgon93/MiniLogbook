package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.domain.convertTo
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.toBgStatus
import com.alexdev.minilogbook.presentation.components.atoms.BgStatusBadge
import com.alexdev.minilogbook.presentation.components.atoms.BgStatusIcon
import com.alexdev.minilogbook.presentation.components.atoms.toStyle
import com.alexdev.minilogbook.presentation.utils.labelRes
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EntryItem(
    modifier: Modifier = Modifier,
    entry: BgEntry,
    unit: BgUnit,
) {
    val displayValue =
        entry.valueInMmol.convertTo(from = BgUnit.MMOL, to = unit).let { "%.2f".format(it) }
    val unitLabel = stringResource(unit.labelRes())
    val formattedTime =
        remember(key1 = entry.timestamp) {
            DateTimeFormatter
                .ofPattern("HH:mm", Locale.getDefault())
                .format(Instant.ofEpochMilli(entry.timestamp).atZone(ZoneId.systemDefault()))
        }
    val status = entry.valueInMmol.toBgStatus()
    val statusStyle = status.toStyle()
    val statusLabel = stringResource(statusStyle.labelRes)
    val contentDescriptionText = "$formattedTime, $displayValue $unitLabel, $statusLabel"

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = contentDescriptionText
                },
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        FlowRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            BgStatusIcon(status = status)
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(40.dp),
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = displayValue,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.testTag(stringResource(R.string.entry_item_value_tag)),
                )
                Text(
                    text = unitLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier =
                        Modifier
                            .padding(bottom = 2.dp)
                            .testTag(stringResource(R.string.entry_item_unit_tag)),
                )
            }
            BgStatusBadge(status = status)
        }
    }
}

@Preview(showBackground = true, name = "mmol/L")
@Composable
private fun EntryItemPreviewMmol() =
    MiniLogbookTheme {
        EntryItem(
            entry =
                BgEntry(
                    id = 1,
                    valueInMmol = 5.5,
                    timestamp = System.currentTimeMillis(),
                ),
            unit = BgUnit.MMOL,
        )
    }

@Preview(showBackground = true, name = "mg/dL")
@Composable
private fun EntryItemPreviewMgDL() =
    MiniLogbookTheme {
        EntryItem(
            entry =
                BgEntry(
                    id = 1,
                    valueInMmol = 5.5,
                    timestamp = System.currentTimeMillis(),
                ),
            unit = BgUnit.MGDL,
        )
    }
