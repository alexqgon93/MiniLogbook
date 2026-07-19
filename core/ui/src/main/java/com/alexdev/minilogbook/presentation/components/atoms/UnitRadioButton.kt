package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.core.ui.R
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.utils.labelRes
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun UnitRadioButton(
    modifier: Modifier = Modifier,
    unit: BgUnit,
    selected: Boolean,
    onSelected: () -> Unit,
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val unitLabel = stringResource(unit.labelRes())
    val selectedStateLabel = stringResource(if (selected) R.string.selected_state else R.string.not_selected_state)

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(size = 8.dp))
                .clickable(onClick = onSelected, role = Role.RadioButton)
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .semantics {
                    contentDescription = "$unitLabel, $selectedStateLabel"
                },
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
        )
        Text(
            text = unitLabel,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        )
    }
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
