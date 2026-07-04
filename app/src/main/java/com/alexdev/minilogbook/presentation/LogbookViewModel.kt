package com.alexdev.minilogbook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdev.minilogbook.domain.convertTo
import com.alexdev.minilogbook.domain.getBgRangeLimits
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.time.TimeProvider
import com.alexdev.minilogbook.domain.usecase.GetBgEntriesUseCase
import com.alexdev.minilogbook.domain.usecase.SaveBgEntryUseCase
import com.alexdev.minilogbook.presentation.LogbookUiEvent.ChangeUnit
import com.alexdev.minilogbook.presentation.LogbookUiEvent.SaveReading
import com.alexdev.minilogbook.presentation.LogbookUiEvent.UpdateInputValue
import com.alexdev.minilogbook.presentation.utils.BgValueFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogbookViewModel
    @Inject
    constructor(
        private val getBgEntriesUseCase: GetBgEntriesUseCase,
        private val saveBgEntryUseCase: SaveBgEntryUseCase,
        private val bgValueFormatter: BgValueFormatter,
        private val timeProvider: TimeProvider,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LogbookUiState())
        val uiState: StateFlow<LogbookUiState> = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                val entriesFlow =
                    getBgEntriesUseCase().catch {
                        _uiState.update { state ->
                            state.copy(inputError = LogbookInputError.LoadFailed)
                        }
                    }

                entriesFlow.collect { entries ->
                    val (displayAvg, avgMmol) = computeAverage(entries, _uiState.value.selectedUnit)
                    _uiState.update {
                        it.copy(
                            entries = entries,
                            averageDisplay = displayAvg,
                            averageMmol = avgMmol,
                        )
                    }
                }
            }
        }

        fun onHandleUiEvent(event: LogbookUiEvent) =
            when (event) {
                is UpdateInputValue -> handleInputChange(input = event.input)
                is ChangeUnit -> handleUnitChange(newUnit = event.unit)
                SaveReading -> handleSave()
            }

        private fun handleInputChange(input: String) {
            _uiState.update {
                it.copy(
                    inputValue = input,
                    inputError =
                        validateInput(
                            input = input,
                            unit = it.selectedUnit,
                            allowEmpty = true,
                        ),
                )
            }
        }

        private fun handleUnitChange(newUnit: BgUnit) {
            val (display, avgMmol) = computeAverage(_uiState.value.entries, newUnit)
            _uiState.update { state ->
                state.copy(
                    selectedUnit = newUnit,
                    inputValue =
                        state.inputValue
                            .toDoubleOrNull()
                            ?.convertTo(from = state.selectedUnit, to = newUnit)
                            ?.let { bgValueFormatter.format(value = it) }
                            ?: state.inputValue,
                    averageDisplay = display,
                    averageMmol = avgMmol,
                )
            }
            handleInputChange(input = _uiState.value.inputValue)
        }

        private fun handleSave() {
            val state = _uiState.value
            val error =
                validateInput(
                    input = state.inputValue,
                    unit = state.selectedUnit,
                    allowEmpty = false,
                )
            if (error != null) {
                _uiState.update { it.copy(inputError = error) }
                return
            }

            val valueMmol =
                requireNotNull(state.inputValue.toDoubleOrNull()).convertTo(
                    from = state.selectedUnit,
                    to = BgUnit.MMOL,
                )

            viewModelScope.launch {
                val result =
                    runCatching {
                        saveBgEntryUseCase(
                            entry =
                                BgEntry(
                                    valueInMmol = valueMmol,
                                    timestamp = timeProvider.now(),
                                ),
                        )
                    }

                result.onSuccess { _uiState.update { it.copy(inputValue = "", inputError = null) } }
                result.onFailure { _uiState.update { it.copy(inputError = LogbookInputError.SaveFailed) } }
            }
        }

        private fun validateInput(
            input: String,
            unit: BgUnit,
            allowEmpty: Boolean,
        ): LogbookInputError? {
            if (input.isBlank()) return if (allowEmpty) null else LogbookInputError.Required
            val parsed = input.toDoubleOrNull() ?: return LogbookInputError.InvalidNumber
            val (min, max) = getBgRangeLimits(unit)
            return when {
                parsed < min -> LogbookInputError.BelowMin(min, unit)
                parsed > max -> LogbookInputError.AboveMax(max, unit)
                else -> null
            }
        }

        private fun computeAverage(
            readings: List<BgEntry>,
            unit: BgUnit,
        ): Pair<String, Double?> {
            if (readings.isEmpty()) return "—" to null
            val avgMmol = readings.map { it.valueInMmol }.average()
            val display =
                avgMmol
                    .convertTo(from = BgUnit.MMOL, to = unit)
                    .let { bgValueFormatter.format(value = it) }
            return display to avgMmol
        }
    }
