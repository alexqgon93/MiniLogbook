package com.alexdev.minilogbook.presentation.utils

import com.alexdev.minilogbook.domain.model.BgUnit
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BgValueFormatterTest {
    private val formatter = BgValueFormatter()

    @Test
    fun `GIVEN double value WHEN format is called THEN returns two decimal string`() {
        formatter.format(value = 5.5) shouldBe "5.50"
    }

    @Test
    fun `GIVEN value with more than two decimals WHEN format is called THEN rounds to two decimals`() {
        formatter.format(value = 5.555) shouldBe "5.56"
    }

    @Test
    fun `GIVEN zero value WHEN format is called THEN returns 0 dot 00`() {
        formatter.format(value = 0.0) shouldBe "0.00"
    }

    @Test
    fun `GIVEN value and MMOL unit WHEN formatWithUnit is called THEN appends mmol label`() {
        formatter.formatWithUnit(value = 5.50, unit = BgUnit.MMOL) shouldBe "5.50 mmol/L"
    }

    @Test
    fun `GIVEN value and MGDL unit WHEN formatWithUnit is called THEN appends mgdl label`() {
        formatter.formatWithUnit(value = 99.0, unit = BgUnit.MGDL) shouldBe "99.00 mg/dL"
    }
}
