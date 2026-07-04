package com.alexdev.minilogbook.presentation.utils

import javax.inject.Inject

class BgValueFormatter
    @Inject
    constructor() {
        fun format(value: Double): String = "%.2f".format(value)

        fun formatWithUnit(
            value: Double,
            unitLabel: String,
        ): String = "${format(value)} $unitLabel"
    }
