package com.alexdev.minilogbook.presentation.utils

import java.util.Locale
import javax.inject.Inject

class BgValueFormatter
    @Inject
    constructor() {
        fun format(value: Double): String = String.format(Locale.US, "%.2f", value)

        fun formatWithUnit(
            value: Double,
            unitLabel: String,
        ): String = "${format(value)} $unitLabel"
    }
