package com.alexdev.minilogbook.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import com.alexdev.minilogbook.presentation.components.molecules.UnitSelector
import com.alexdev.minilogbook.presentation.components.organisms.AverageHeader
import com.alexdev.minilogbook.presentation.components.organisms.BgInputRow
import com.alexdev.minilogbook.presentation.components.organisms.EntryList
import com.alexdev.minilogbook.presentation.preview.previewEntriesList
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme

@Composable
fun LogbookScreen(viewModel: LogbookViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LogbookContent(
        state = state,
        onEvent = viewModel::onHandleUiEvent,
    )
}

@Composable
internal fun LogbookContent(
    state: LogbookUiState,
    onEvent: (LogbookUiEvent) -> Unit,
) = Scaffold { paddingValues ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        AverageHeader(averageDisplay = state.averageDisplay, averageMmol = state.averageMmol, unit = state.selectedUnit)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(id = R.string.add_measurement),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "Unit of measure",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            UnitSelector(
                selected = state.selectedUnit,
                onUnitSelected = { onEvent(LogbookUiEvent.ChangeUnit(it)) },
            )
            Text(
                text = "Glucose value",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            BgInputRow(
                value = state.inputValue,
                unitLabel = state.selectedUnit.label(),
                error = state.inputError,
                onValueChange = { onEvent(LogbookUiEvent.UpdateInputValue(input = it)) },
                onSave = { onEvent(LogbookUiEvent.SaveReading) },
            )
        }
        EntryList(
            modifier =
                Modifier
                    .weight(1f)
                    .testTag(stringResource(id = R.string.entry_list_tag)),
            entries = state.entries,
            unit = state.selectedUnit,
        )
    }
}

@Preview(showBackground = true, name = "mmol/L with readings")
@Composable
private fun LogbookPreviewMmol() =
    MiniLogbookTheme {
        LogbookContent(
            state =
                LogbookUiState(
                    selectedUnit = BgUnit.MMOL,
                    inputValue = "5.5",
                    averageDisplay = "5.50",
                    entries = previewEntriesList(),
                ),
            onEvent = {},
        )
    }

@Preview(showBackground = true, name = "mg/dL with readings")
@Composable
private fun LogbookPreviewMgDl() =
    MiniLogbookTheme {
        LogbookContent(
            state =
                LogbookUiState(
                    selectedUnit = BgUnit.MGDL,
                    inputValue = "99.10",
                    averageDisplay = "122.00",
                    entries = previewEntriesList(),
                ),
            onEvent = {},
        )
    }

@Preview(showBackground = true, name = "Input Error")
@Composable
private fun LogbookPreviewError() =
    MiniLogbookTheme {
        LogbookContent(
            state =
                LogbookUiState(
                    selectedUnit = BgUnit.MMOL,
                    inputValue = "-3",
                    inputError = "Must be >= 0",
                    averageDisplay = "_",
                    entries = emptyList(),
                ),
            onEvent = {},
        )
    }

@Preview(showBackground = true, name = "Empty State")
@Composable
private fun LogbookPreviewEmpty() =
    MiniLogbookTheme {
        LogbookContent(
            state = LogbookUiState(),
            onEvent = {},
        )
    }
