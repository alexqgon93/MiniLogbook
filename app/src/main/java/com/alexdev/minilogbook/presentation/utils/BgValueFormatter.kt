package com.alexdev.minilogbook.presentation.utils

import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import javax.inject.Inject

class BgValueFormatter @Inject constructor() {
    fun format(value: Double): String = "%.2f".format(value)
    fun formatWithUnit(value: Double, unit: BgUnit): String = "${format(value)} ${unit.label()}"
}
