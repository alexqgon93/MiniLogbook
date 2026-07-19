package com.alexdev.minilogbook.domain

import com.alexdev.minilogbook.domain.model.BgUnit

private const val CONVERSION_FACTOR = 18.0182
const val BG_MIN_MMOL = 2.8
const val BG_MIN_MG_DL = BG_MIN_MMOL * CONVERSION_FACTOR
const val BG_MAX_MMOL = 33.3
const val BG_MAX_MG_DL = BG_MAX_MMOL * CONVERSION_FACTOR

fun Double.toMgdl(): Double = this * CONVERSION_FACTOR

fun Double.toMmol(): Double = this / CONVERSION_FACTOR

fun Double.convertTo(
    from: BgUnit,
    to: BgUnit,
): Double =
    when (from) {
        to -> this
        BgUnit.MMOL -> toMgdl()
        else -> toMmol()
    }

fun getBgRangeLimits(unit: BgUnit): Pair<Double, Double> =
    if (unit == BgUnit.MMOL) {
        BG_MIN_MMOL to BG_MAX_MMOL
    } else {
        BG_MIN_MG_DL to BG_MAX_MG_DL
    }
