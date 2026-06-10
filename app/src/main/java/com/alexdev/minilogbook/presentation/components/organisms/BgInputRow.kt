package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.presentation.components.atoms.InputErrorText
import com.alexdev.minilogbook.presentation.components.molecules.BgValueTextField
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun BgInputRow(
    value: String,
    unitLabel: String,
    error: String?,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit,
) = Column {
    BgValueTextField(
        value = value,
        unitLabel = unitLabel,
        isError = error != null,
        onValueChange = onValueChange
    )
    InputErrorText(error = error)
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onSave,
            enabled = error == null && value.isNotEmpty(),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Save", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true, name = "Idle - mmol/L")
@Composable
private fun BgInputRowPreviewIdle() = MiniLogbookTheme {
    BgInputRow(
        value = "5.5",
        unitLabel = "mmol/L",
        error = null,
        onValueChange = {},
        onSave = {},
    )
}

@Preview(showBackground = true, name = "Error state")
@Composable
private fun BgInputRowPreviewError() = MiniLogbookTheme {
    BgInputRow(
        value = "-3",
        unitLabel = "mmol/L",
        error = "Must be >= 0",
        onValueChange = {},
        onSave = {},
    )
}

@Preview(showBackground = true, name = "Empty - Save disabled")
@Composable
private fun BgInputRowPreviewEmpty() = MiniLogbookTheme {
    BgInputRow(
        value = "",
        unitLabel = "mg/dL",
        error = null,
        onValueChange = {},
        onSave = {},
    )
}
