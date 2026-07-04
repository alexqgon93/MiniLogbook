package com.alexdev.minilogbook.presentation.utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Locale

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
        formatter.formatWithUnit(value = 5.50, unitLabel = "mmol/L") shouldBe "5.50 mmol/L"
    }

    @Test
    fun `GIVEN value and MGDL unit WHEN formatWithUnit is called THEN appends mgdl label`() {
        formatter.formatWithUnit(value = 99.0, unitLabel = "mg/dL") shouldBe "99.00 mg/dL"
    }

    @Test
    fun `GIVEN locale with comma decimal separator WHEN format is called THEN uses dot decimal separator`() {
        val defaultLocale = Locale.getDefault()
        Locale.setDefault(Locale.GERMANY)
        try {
            formatter.format(value = 5.5) shouldBe "5.50"
        } finally {
            Locale.setDefault(defaultLocale)
        }
    }
}
