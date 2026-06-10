package com.alexdev.minilogbook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdev.minilogbook.domain.convertTo
import com.alexdev.minilogbook.domain.getBgRangeLimits
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.usecase.GetBgEntriesUseCase
import com.alexdev.minilogbook.domain.usecase.SaveBgEntryUseCase
import com.alexdev.minilogbook.presentation.LogbookUiEvent.UpdateInputValue
import com.alexdev.minilogbook.presentation.LogbookUiEvent.ChangeUnit
import com.alexdev.minilogbook.presentation.LogbookUiEvent.SaveReading
import com.alexdev.minilogbook.presentation.utils.BgValueFormatter
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
    private val bgValueFormatter: BgValueFormatter,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogbookUiState())
    val uiState: StateFlow<LogbookUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            getBgEntriesUseCase().collect { entries ->
                _uiState.update {
                    it.copy(
                        entries = entries,
                        averageDisplay = formatAverage(entries = entries, unit = it.selectedUnit)
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

    private fun handleInputChange(input: String) {
        _uiState.update {
            it.copy(
                inputValue = input, inputError = validateInput(
                    input = input,
                    unit = it.selectedUnit,
                    allowEmpty = true
                )
            )
        }
    }

    private fun handleUnitChange(newUnit: BgUnit) {
        _uiState.update { state ->
            state.copy(
                selectedUnit = newUnit,
                inputValue = state.inputValue.toDoubleOrNull()
                    ?.convertTo(from = state.selectedUnit, to = newUnit)
                    ?.let { bgValueFormatter.format(value = it) }
                    ?: state.inputValue,
                averageDisplay = formatAverage(entries = state.entries, unit = newUnit)
            )
        }
        handleInputChange(input = _uiState.value.inputValue)
    }

    private fun handleSave() {
        val state = _uiState.value
        val error =
            validateInput(input = state.inputValue, unit = state.selectedUnit, allowEmpty = false)
        if (error != null) {
            _uiState.update { it.copy(inputError = error) }
            return
        }

        val valueMmol = requireNotNull(state.inputValue.toDoubleOrNull()).convertTo(
            from = state.selectedUnit,
            to = BgUnit.MMOL
        )

        viewModelScope.launch {
            runCatching { saveBgEntryUseCase(entry = BgEntry(valueInMmol = valueMmol)) }
                .onSuccess { _uiState.update { it.copy(inputValue = "", inputError = null) } }
                .onFailure { _uiState.update { it.copy(inputError = ERROR_SAVE_FAILED) } }
        }
    }

    private fun validateInput(input: String, unit: BgUnit, allowEmpty: Boolean): String? {
        if (input.isBlank()) return if (allowEmpty) null else ERROR_REQUIRED
        val parsed = input.toDoubleOrNull() ?: return ERROR_INVALID_NUMBER
        val (min, max) = getBgRangeLimits(unit)
        return when {
            parsed < min -> errorBelowMin(min, unit)
            parsed > max -> errorAboveMax(max, unit)
            else -> null
        }
    }

    private fun errorBelowMin(min: Double, unit: BgUnit) =
        "Must be >= ${bgValueFormatter.formatWithUnit(value = min, unit = unit)}"

    private fun errorAboveMax(max: Double, unit: BgUnit) =
        "Must be <= ${bgValueFormatter.formatWithUnit(value = max, unit = unit)}"

    private fun computeAverageMmol(entries: List<BgEntry>): Double? =
        if (entries.isEmpty()) null else entries.map { it.valueInMmol }.average()

    private fun formatAverage(entries: List<BgEntry>, unit: BgUnit): String =
        computeAverageMmol(entries)
            ?.convertTo(from = BgUnit.MMOL, to = unit)
            ?.let { bgValueFormatter.format(value = it) }
            ?: "-"

    companion object {
        private const val ERROR_REQUIRED = "Value is required"
        private const val ERROR_INVALID_NUMBER = "Invalid number"
        private const val ERROR_SAVE_FAILED = "Failed to save. Please try again."
    }
}
