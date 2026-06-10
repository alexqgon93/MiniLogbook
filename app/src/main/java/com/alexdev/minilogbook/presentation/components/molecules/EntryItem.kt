package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun EntryItem(
    entry: BgEntry,
    unit: BgUnit,
) {
    val displayValue =
        entry.valueInMmol.convertTo(from = BgUnit.MMOL, to = unit).let { "%.2f".format(it) }
    val formattedTime =
        remember(entry.timestamp) {
            SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date(entry.timestamp))
        }
    val (backgroundColor, textColor) = getGlucoseStatusColors(entry.valueInMmol, unit = unit)

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .animateContentSize()
                .background(color = backgroundColor, shape = RoundedCornerShape(size = 8.dp))
                .padding(all = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = formattedTime, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = "$displayValue ${unit.label()}",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            fontWeight = FontWeight.Bold,
        )
    }
}

private fun getGlucoseStatusColors(
    valueInMmol: Double,
    unit: BgUnit,
): Pair<Color, Color> {
    val (lowerThreshold, normalMax, highMax) =
        when (unit) {
            BgUnit.MMOL -> Triple(4.0, 7.0, 11.0)
            BgUnit.MGDL -> Triple(72.07, 126.13, 198.20)
        }
    val displayValue = valueInMmol.convertTo(from = BgUnit.MMOL, to = unit)
    return when {
        displayValue < lowerThreshold -> Color(0xFFFFEBEE) to Color(0xFFC62828)
        displayValue <= normalMax -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        displayValue <= highMax -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        else -> Color(0xFFFFEBEE) to Color(0xFF7F0000)
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
