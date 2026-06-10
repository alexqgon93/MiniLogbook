package com.alexdev.minilogbook.data

import com.alexdev.minilogbook.data.local.BgEntryEntity
import com.alexdev.minilogbook.data.mapper.toDomainModel
import com.alexdev.minilogbook.data.mapper.toEntry
import com.alexdev.minilogbook.domain.model.BgEntry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BgEntryMapperTest {

    @Test
    fun `GIVEN domain entry model WHEN toEntry called THEN return entity`() {
        val domain = BgEntry(id = 1, valueInMmol = 10.11, timestamp = 3580)
        val result = domain.toEntry()
        assertEquals(domain.id, result.id)
        assertEquals(domain.valueInMmol, result.valueInMmol)
        assertEquals(domain.timestamp, result.timestamp)
    }

    @Test
    fun `GIVEN entity model WHEN toDomainModel then domain model is returned`() {
        val entity = BgEntryEntity(id = 8859, valueInMmol = 14.15, timestamp = 6441)
        val result = entity.toDomainModel()
        assertEquals(entity.id, result.id)
        assertEquals(entity.valueInMmol, result.valueInMmol)
        assertEquals(entity.timestamp, result.timestamp)
    }

}