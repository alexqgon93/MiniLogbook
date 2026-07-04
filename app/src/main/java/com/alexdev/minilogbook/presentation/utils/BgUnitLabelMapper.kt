package com.alexdev.minilogbook.presentation.utils

import com.alexdev.minilogbook.R
import com.alexdev.minilogbook.domain.model.BgUnit

fun BgUnit.labelRes() =
    when (this) {
        BgUnit.MMOL -> R.string.mmol_l
        BgUnit.MGDL -> R.string.mg_dl
    }
