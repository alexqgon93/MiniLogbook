package com.alexdev.minilogbook.domain.model

import com.alexdev.minilogbook.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BgUnitTest {
    @Test
    fun `GIVEN MMOL unit WHEN label is requested THEN returns mmol_L`() {
        assertEquals(R.string.mmol_l, BgUnit.MMOL.labelRes())
    }

    @Test
    fun `GIVEN MGDL unit WHEN label is requested THEN returns mg_dL`() {
        assertEquals(R.string.mg_dl, BgUnit.MGDL.labelRes())
    }

    @Test
    fun `GIVEN all bg units WHEN labels are requested THEN all labels are non blank`() {
        BgUnit.entries.forEach { unit ->
            check(unit.labelRes() != 0) { "Label resource is blank for $unit" }
        }
    }
}
