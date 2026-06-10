package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.domain.convertTo
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EntryItem(entry: BgEntry, unit: BgUnit) {
    val displayValue =
        entry.valueInMmol.convertTo(from = BgUnit.MMOL, to = unit).let { "%.2f".format(it) }
    val formattedTime = remember(entry.timestamp) {
        SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date(entry.timestamp))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = formattedTime, style = MaterialTheme.typography.bodySmall)
        Text(text = "$displayValue ${unit.label()}", style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true, name = "mmol/L")
@Composable
private fun EntryItemPreviewMmol() = MiniLogbookTheme {
    EntryItem(
        entry = BgEntry(
            id = 1,
            valueInMmol = 5.5,
            timestamp = System.currentTimeMillis()
        ),
        unit = BgUnit.MMOL
    )
}

@Preview(showBackground = true, name = "mg/dL")
@Composable
private fun EntryItemPreviewMgDL() = MiniLogbookTheme {
    EntryItem(
        entry = BgEntry(
            id = 1,
            valueInMmol = 5.5,
            timestamp = System.currentTimeMillis()
        ),
        unit = BgUnit.MGDL
    )
}
