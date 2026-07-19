package com.alexdev.minilogbook.presentation.utils

import com.alexdev.minilogbook.core.ui.R
import com.alexdev.minilogbook.domain.model.BgUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BgUnitLabelMapperTest {
    @Test
    fun `GIVEN MMOL unit WHEN label is requested THEN returns mmol resource`() {
        assertEquals(R.string.mmol_l, BgUnit.MMOL.labelRes())
    }

    @Test
    fun `GIVEN MGDL unit WHEN label is requested THEN returns mgdl resource`() {
        assertEquals(R.string.mg_dl, BgUnit.MGDL.labelRes())
    }

    @Test
    fun `GIVEN all bg units WHEN labels are requested THEN all labels are non blank`() {
        BgUnit.entries.forEach { unit ->
            check(unit.labelRes() != 0) { "Label resource is blank for $unit" }
        }
    }
}
