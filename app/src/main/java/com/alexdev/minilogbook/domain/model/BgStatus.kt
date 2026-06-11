package com.alexdev.minilogbook.domain.model

enum class BgStatus { LOW, NORMAL, ELEVATED, HIGH }

fun Double.toBgStatus(): BgStatus =
    when {
        this < 2.8 -> BgStatus.LOW
        this < 7.8 -> BgStatus.NORMAL
        this < 10.0 -> BgStatus.ELEVATED
        else -> BgStatus.HIGH
    }
