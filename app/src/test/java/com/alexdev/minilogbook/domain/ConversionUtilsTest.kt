package com.alexdev.minilogbook.domain

import com.alexdev.minilogbook.domain.model.BgUnit
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ConversionUtilsTest {

    @Test
    fun `GIVEN mmol value WHEN toMgdl is called THEN multiplies by conversion factor`() {
        5.0.toMgdl() shouldBe (90.091 plusOrMinus 1e-6)
    }

    @Test
    fun `GIVEN mgdl value WHEN toMmol is called THEN divides by conversion factor`() {
        180.182.toMmol() shouldBe (10.0 plusOrMinus 1e-6)
    }

    @Test
    fun `GIVEN same source and target unit WHEN convertTo is called THEN returns same value`() {
        7.25.convertTo(from = BgUnit.MMOL, to = BgUnit.MMOL) shouldBe 7.25
        7.25.convertTo(from = BgUnit.MGDL, to = BgUnit.MGDL) shouldBe 7.25
    }

    @Test
    fun `GIVEN mmol source and mgdl target WHEN convertTo is called THEN converts to mgdl`() {
        6.0.convertTo(from = BgUnit.MMOL, to = BgUnit.MGDL) shouldBe (108.1092 plusOrMinus 1e-6)
    }

    @Test
    fun `GIVEN mgdl source and mmol target WHEN convertTo is called THEN converts to mmol`() {
        108.1092.convertTo(from = BgUnit.MGDL, to = BgUnit.MMOL) shouldBe (6.0 plusOrMinus 1e-6)
    }

    @Test
    fun `GIVEN mmol unit WHEN getBgRangeLimits is called THEN returns mmol min and max`() {
        val (min, max) = getBgRangeLimits(unit = BgUnit.MMOL)
        min shouldBe (2.8 plusOrMinus 1e-6)
        max shouldBe (33.0 plusOrMinus 1e-6)
    }

    @Test
    fun `GIVEN mgdl unit WHEN getBgRangeLimits is called THEN returns converted mgdl min and max`() {
        val (min, max) = getBgRangeLimits(unit = BgUnit.MGDL)
        min shouldBe (BG_MIN_MMOL * 18.0182 plusOrMinus 1e-6)
        max shouldBe (BG_MAX_MMOL * 18.0182 plusOrMinus 1e-6)
    }

    @Test
    fun `GIVEN exported range constants WHEN checked THEN mgdl constants match mmol conversion`() {
        BG_MIN_MG_DL shouldBe (BG_MIN_MMOL * 18.0182 plusOrMinus 1e-6)
        BG_MAX_MG_DL shouldBe (BG_MAX_MMOL * 18.0182 plusOrMinus 1e-6)
    }
}
