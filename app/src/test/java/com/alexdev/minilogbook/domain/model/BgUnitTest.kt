package com.alexdev.minilogbook.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BgUnitTest {
    @Test
    fun `GIVEN MMOL unit WHEN label is requested THEN returns mmol_L`() = assertEquals("mmol/L", BgUnit.MMOL.label())

    @Test
    fun `GIVEN MGDL unit WHEN label is requested THEN returns mg_dL`() = assertEquals("mg/dL", BgUnit.MGDL.label())

    @Test
    fun `GIVEN all bg units WHEN labels are requested THEN all labels are non blank`() =
        BgUnit.entries.forEach { unit ->
            check(unit.label().isNotBlank()) { "Label is blank for $unit" }
        }
}
