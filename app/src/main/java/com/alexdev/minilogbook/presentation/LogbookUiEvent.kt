package com.alexdev.minilogbook.presentation

import com.alexdev.minilogbook.domain.model.BgUnit

sealed class LogbookUiEvent {
    data class UpdateInputValue(val input: String) : LogbookUiEvent()
    data class ChangeUnit(val unit: BgUnit) : LogbookUiEvent()
    object SaveReading : LogbookUiEvent()
}