package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun BgValueTextField(
    modifier: Modifier = Modifier,
    value: String,
    unitLabel: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = unitLabel,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

@Preview(showBackground = true, name = "Idle")
@Composable
private fun BgValueTextFieldPreviewIdle() =
    MiniLogbookTheme {
        BgValueTextField(
            value = "5.5",
            unitLabel = stringResource(id = R.string.mmol_l),
            isError = false,
            onValueChange = {},
        )
    }

@Preview(showBackground = true, name = "Error state")
@Composable
private fun BgValueTextFieldPreviewError() =
    MiniLogbookTheme {
        BgValueTextField(
            value = "-3",
            unitLabel = stringResource(id = R.string.mmol_l),
            isError = true,
            onValueChange = {},
        )
    }

@Preview(showBackground = true, name = "mg/dL label")
@Composable
private fun BgValueTextFieldPreviewMgDl() =
    MiniLogbookTheme {
        BgValueTextField(
            value = "99.10",
            unitLabel = stringResource(id = R.string.mmg_dl),
            isError = false,
            onValueChange = {},
        )
    }
