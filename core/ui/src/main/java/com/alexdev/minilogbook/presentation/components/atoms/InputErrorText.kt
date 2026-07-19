package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun InputErrorText(
    error: String?,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = error != null,
        modifier = modifier,
    ) {
        Text(
            text = error.orEmpty(),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp),
        )
    }
}

@Preview(showBackground = true, name = "Error visible")
@Composable
private fun InputErrorTextPreviewVisible() = MiniLogbookTheme { InputErrorText(error = "Must be >= 0") }
