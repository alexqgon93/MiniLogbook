package com.alexdev.minilogbook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdev.minilogbook.domain.convertTo
import com.alexdev.minilogbook.domain.getBgRangeLimits
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import com.alexdev.minilogbook.domain.usecase.GetBgEntriesUseCase
import com.alexdev.minilogbook.domain.usecase.SaveBgEntryUseCase
import com.alexdev.minilogbook.presentation.LogbookUiEvent.UpdateInputValue
import com.alexdev.minilogbook.presentation.LogbookUiEvent.ChangeUnit
import com.alexdev.minilogbook.presentation.LogbookUiEvent.SaveReading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogbookViewModel @Inject constructor(
    private val getBgEntriesUseCase: GetBgEntriesUseCase,
    private val saveBgEntryUseCase: SaveBgEntryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogbookUiState())
    val uiState: StateFlow<LogbookUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            getBgEntriesUseCase().collect { entries ->
                _uiState.update {
                    it.copy(
                        entries = entries,
                        averageDisplay = computeAverage(entries = entries, unit = BgUnit.MMOL)
                    )
                }
            }
        }
    }

    fun onHandleUiEvent(event: LogbookUiEvent) = when (event) {
        is UpdateInputValue -> handleInputChange(input = event.input)
        is ChangeUnit -> handleUnitChange(newUnit = event.unit)
        SaveReading -> handleSave()
    }

    fun handleInputChange(input: String) {
        val parsed = input.toDoubleOrNull()
        val unit = _uiState.value.selectedUnit
        val (min, max) = getBgRangeLimits(unit)

        val error = when {
            input.isEmpty() -> null
            parsed == null -> "Invalid number"
            parsed < min -> "Must be >= ${"%.1f".format(min)} ${unit.label()}"
            parsed > max -> "Must be <= ${"%.1f".format(max)} ${unit.label()}"
            else -> null
        }
        _uiState.update { it.copy(inputValue = input, inputError = error) }
    }

    fun handleUnitChange(newUnit: BgUnit) {
        _uiState.update { state ->
            state.copy(
                selectedUnit = newUnit,
                inputValue = state.inputValue.toDoubleOrNull()
                    ?.convertTo(from = state.selectedUnit, to = newUnit)
                    ?.let { formatValue(it) }
                    ?: state.inputValue,
                averageDisplay = computeAverage(state.entries, newUnit)
            )
        }
        handleInputChange(_uiState.value.inputValue)
    }

    fun handleSave() {
        val state = _uiState.value
        val parsedValue = state.inputValue.toDoubleOrNull()

        val inputError = when {
            state.inputValue.isBlank() -> "Value is required"
            parsedValue == null -> "Invalid number"
            parsedValue < 0 -> "Must be >= 0"
            else -> null
        }

        if (inputError != null) {
            _uiState.update { it.copy(inputError = inputError) }
            return
        }

        val valueMmol =
            requireNotNull(parsedValue).convertTo(from = state.selectedUnit, to = BgUnit.MMOL)

        viewModelScope.launch {
            saveBgEntryUseCase(entry = BgEntry(valueInMmol = valueMmol))
            _uiState.update { it.copy(inputValue = "", inputError = null) }
        }
    }

    private fun computeAverage(entries: List<BgEntry>, unit: BgUnit): String {
        if (entries.isEmpty()) return "-"
        val avgMmol = entries.map { it.valueInMmol }.average()
        return formatValue(avgMmol.convertTo(from = BgUnit.MMOL, to = unit))
    }

    private fun formatValue(value: Double): String = "%.2f".format(value)
}