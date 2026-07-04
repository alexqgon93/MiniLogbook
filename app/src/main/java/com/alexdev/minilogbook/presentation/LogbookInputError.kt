package com.alexdev.minilogbook.presentation

import com.alexdev.minilogbook.domain.model.BgUnit

sealed class LogbookInputError {
    data object Required : LogbookInputError()

    data object InvalidNumber : LogbookInputError()

    data class BelowMin(
        val min: Double,
        val unit: BgUnit,
    ) : LogbookInputError()

    data class AboveMax(
        val max: Double,
        val unit: BgUnit,
    ) : LogbookInputError()

    data object SaveFailed : LogbookInputError()

    data object LoadFailed : LogbookInputError()
}
