package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.molecules.EntryItem
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EntryList(
    modifier: Modifier = Modifier,
    entries: List<BgEntry>,
    unit: BgUnit,
) {
    val grouped =
        remember(entries) {
            entries
                .groupBy { entry ->
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(entry.timestamp)
                }.toSortedMap(reverseOrder())
        }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp),
    ) {
        grouped.forEach { (dateKey, entries) ->
            item(key = dateKey) {
                EntriesDateHeader(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp), dateKey = dateKey)
            }
            items(entries, key = { it.id }) { entry ->
                EntryItem(entry = entry, unit = unit)
            }
        }
        item {
            Text(
                text = stringResource(R.string.reference_values_may_vary),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun EntriesDateHeader(
    modifier: Modifier = Modifier,
    dateKey: String,
) {
    val dateDisplay =
        remember(dateKey) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.parse(dateKey) ?: return@remember (dateKey to false)
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            if (dateKey == today) {
                SimpleDateFormat("MMMM d", Locale.getDefault()).format(date) to true
            } else {
                SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(date) to false
            }
        }
    val displayDate =
        if (dateDisplay.second) {
            stringResource(R.string.today_prefix, dateDisplay.first)
        } else {
            dateDisplay.first
        }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = displayDate,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Icon(
            imageVector = Icons.Outlined.CalendarMonth,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(18.dp),
        )
    }
}

private fun previewEntriesList() =
    listOf(
        BgEntry(id = 1, valueInMmol = 5.5, timestamp = System.currentTimeMillis() - 3_600_000),
        BgEntry(id = 2, valueInMmol = 6.2, timestamp = System.currentTimeMillis() - 7_200_000),
        BgEntry(id = 3, valueInMmol = 4.8, timestamp = System.currentTimeMillis() - 10_800_000),
    )

@Preview(showBackground = true, name = "mmol/L")
@Composable
private fun EntryListPreviewMmol() =
    MiniLogbookTheme {
        EntryList(
            entries = previewEntriesList(),
            unit = BgUnit.MMOL,
        )
    }

@Preview(showBackground = true, name = "mg/dL")
@Composable
private fun EntryListPreviewMgDl() =
    MiniLogbookTheme {
        EntryList(
            entries = previewEntriesList(),
            unit = BgUnit.MGDL,
        )
    }

@Preview(showBackground = true, name = "Empty List")
@Composable
private fun EntryListPreviewEmpty() =
    MiniLogbookTheme {
        EntryList(
            entries = emptyList(),
            unit = BgUnit.MMOL,
        )
    }
