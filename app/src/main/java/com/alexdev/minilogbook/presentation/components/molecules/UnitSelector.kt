package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.components.atoms.UnitRadioButton
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun UnitSelector(
    selected: BgUnit,
    onUnitSelected: (BgUnit) -> Unit,
) = Column {
    BgUnit.entries.forEach { unit ->
        UnitRadioButton(
            unit = unit,
            selected = selected == unit,
            onSelected = { onUnitSelected(unit) },
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
