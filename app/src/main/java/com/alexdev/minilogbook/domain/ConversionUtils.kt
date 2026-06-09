package com.alexdev.minilogbook.domain

import com.alexdev.minilogbook.domain.model.BgUnit

private val CONVERSION_FACTOR = 18.0182

fun Double.toMgdl(): Double = this * CONVERSION_FACTOR

fun Double.toMmol(): Double = this / CONVERSION_FACTOR

fun Double.convertTo(from: BgUnit, to: BgUnit): Double = when (from) {
    to -> this
    BgUnit.MMOL -> toMgdl()
    else -> toMmol()
}