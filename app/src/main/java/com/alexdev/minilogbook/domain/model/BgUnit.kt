package com.alexdev.minilogbook.domain.model

import com.alexdev.minilogbook.R

enum class BgUnit { MMOL, MGDL }

fun BgUnit.labelRes() =
    when (this) {
        BgUnit.MMOL -> R.string.mmol_l
        BgUnit.MGDL -> R.string.mg_dl
    }
