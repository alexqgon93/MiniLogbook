package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.presentation.components.atoms.InputErrorText
import com.alexdev.minilogbook.presentation.components.molecules.BgValueTextField
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun BgInputRow(
    modifier: Modifier = Modifier,
    value: String,
    unitLabel: String,
    error: String?,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(12.dp),
) {
    BgValueTextField(
        value = value,
        unitLabel = unitLabel,
        isError = error != null,
        onValueChange = onValueChange,
    )
    InputErrorText(error = error)
    Button(
        onClick = onSave,
        enabled = error == null && value.isNotEmpty(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = 18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Icon(
            imageVector = Icons.Filled.WaterDrop,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Save reading",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Preview(showBackground = true, name = "Idle - mmol/L")
@Composable
private fun BgInputRowPreviewIdle() =
    MiniLogbookTheme {
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
private fun BgInputRowPreviewError() =
    MiniLogbookTheme {
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
private fun BgInputRowPreviewEmpty() =
    MiniLogbookTheme {
        BgInputRow(
            value = "",
            unitLabel = "mg/dL",
            error = null,
            onValueChange = {},
            onSave = {},
        )
    }
