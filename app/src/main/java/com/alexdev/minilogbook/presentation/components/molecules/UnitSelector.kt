package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.atoms.UnitRadioButton
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun UnitSelector(
    modifier: Modifier = Modifier,
    selected: BgUnit,
    onUnitSelected: (BgUnit) -> Unit,
) = Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    BgUnit.entries.forEach { unit ->
        UnitRadioButton(
            unit = unit,
            selected = selected == unit,
            onSelected = { onUnitSelected(unit) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true, name = "mmol/L selected")
@Composable
fun UnitSelectorPreviewMmol() =
    MiniLogbookTheme {
        UnitSelector(selected = BgUnit.MMOL, onUnitSelected = {})
    }

@Preview(showBackground = true, name = "mg/dL selected")
@Composable
fun UnitSelectorPreviewMgDl() =
    MiniLogbookTheme {
        UnitSelector(selected = BgUnit.MGDL, onUnitSelected = {})
    }
