package com.alexdev.minilogbook.domain.model

enum class BgUnit { MMOL, MGDL }

fun BgUnit.label() =
    when (this) {
        BgUnit.MMOL -> "mmol/L"
        BgUnit.MGDL -> "mg/dL"
    }
