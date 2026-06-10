package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun AverageHeader(average: String, unit: BgUnit) {
    val (background, textColor) = getAverageStatusColors(average = average, unit = unit)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = background,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.your_average_is, average, unit.label()),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}

private fun getAverageStatusColors(average: String, unit: BgUnit): Pair<Color, Color> {
    val value = average.toDoubleOrNull() ?: return Color(0xFFF5F5F5) to Color(0xFF757575)
    val (lowerThreshold, normalMax, highMax) = when (unit) {
        BgUnit.MMOL -> Triple(4.0, 7.0, 11.0)
        BgUnit.MGDL -> Triple(72.07, 126.13, 198.20)
    }

    return when {
        average == "--" -> Color(0xFFF5F5F5) to Color(0xFF9E9E9E)
        value < lowerThreshold -> Color(0xFFFFEBEE) to Color(0xFFC62828)
        value <= normalMax -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        value <= highMax -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        else -> Color(0xFFFFEBEE) to Color(0xFF7F0000)
    }
}

@Preview(showBackground = true, name = "mmol/L")
@Composable
private fun AverageHeaderPreviewMmol() = MiniLogbookTheme {
    AverageHeader(average = "5.50", unit = BgUnit.MMOL)
}

@Preview(showBackground = true, name = "mg/dL")
@Composable
private fun AverageHeaderPreviewMgDl() = MiniLogbookTheme {
    AverageHeader(average = "122.00", unit = BgUnit.MGDL)
}

@Preview(showBackground = true, name = "Empty state")
@Composable
private fun AverageHeaderPreviewEmpty() = MiniLogbookTheme {
    AverageHeader(average = "--", unit = BgUnit.MMOL)
}