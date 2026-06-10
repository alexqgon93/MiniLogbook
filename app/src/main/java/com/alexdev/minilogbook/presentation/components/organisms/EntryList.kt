package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.molecules.EntryItem
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun EntryList(
    entries: List<BgEntry>,
    unit: BgUnit,
) = LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    items(entries, key = { it.id }) { entry ->
        EntryItem(entry = entry, unit = unit)
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
