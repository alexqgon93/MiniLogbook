package com.alexdev.minilogbook.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BgEntryTest {
    @Test
    fun `given value only when BgEntry is created then id defaults to zero`() {
        val valueInMmol = 5.5
        val entry = BgEntry(valueInMmol = valueInMmol)
        assertEquals(0L, entry.id)
    }

    @Test
    fun `given value only when BgEntry is created then timestamp defaults to current time`() {
        val beforeCreation = System.currentTimeMillis()
        val entry = BgEntry(valueInMmol = 5.5)
        val afterCreation = System.currentTimeMillis()
        assertTrue(entry.timestamp in beforeCreation..afterCreation)
    }

    @Test
    fun `given all constructor values when BgEntry is created then keeps provided values`() {
        val id = 9L
        val valueInMmol = 6.7
        val timestamp = 1_717_000_000_000L
        val entry =
            BgEntry(
                id = id,
                valueInMmol = valueInMmol,
                timestamp = timestamp,
            )
        assertEquals(id, entry.id)
        assertEquals(valueInMmol, entry.valueInMmol)
        assertEquals(timestamp, entry.timestamp)
    }

    @Test
    fun `given same field values when comparing two entries then they are equal`() {
        val first = BgEntry(id = 1L, valueInMmol = 5.5, timestamp = 1000L)
        val second = BgEntry(id = 1L, valueInMmol = 5.5, timestamp = 1000L)
        assertEquals(first, second)
    }
}
