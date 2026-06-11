package com.alexdev.minilogbook.presentation

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit

data class LogbookUiState(
    val selectedUnit: BgUnit = BgUnit.MMOL,
    val inputValue: String = "",
    val entries: List<BgEntry> = emptyList(),
    val averageDisplay: String = "—",
    val averageMmol: Double? = null,
    val inputError: String? = null,
)
