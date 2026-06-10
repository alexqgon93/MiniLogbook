package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun AverageHeader(average: String, unit: BgUnit) = Column {
    Text(
        text = stringResource(id = R.string.your_average_is, average, unit.label()),
        style = MaterialTheme.typography.bodyLarge
    )
    HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
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