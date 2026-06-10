package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun UnitRadioButton(
    modifier: Modifier = Modifier,
    unit: BgUnit,
    selected: Boolean,
    onSelected: () -> Unit,
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier =
        modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
            .padding(vertical = 4.dp),
) {
    RadioButton(selected = selected, onClick = onSelected)
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = unit.label(),
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Preview(showBackground = true, name = "Selected")
@Composable
private fun UnitRadioButtonPreviewSelected() =
    MiniLogbookTheme {
        UnitRadioButton(unit = BgUnit.MMOL, selected = true, onSelected = {})
    }

@Preview(showBackground = true, name = "Unselected")
@Composable
private fun UnitRadioButtonPreviewUnSelected() =
    MiniLogbookTheme {
        UnitRadioButton(unit = BgUnit.MGDL, selected = false, onSelected = {})
    }
