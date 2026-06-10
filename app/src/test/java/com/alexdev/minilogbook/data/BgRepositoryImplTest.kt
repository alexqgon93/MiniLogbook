package com.alexdev.minilogbook.data

import com.alexdev.minilogbook.data.local.BgEntryDao
import com.alexdev.minilogbook.data.local.BgEntryEntity
import com.alexdev.minilogbook.data.repository.BgRepositoryImpl
import com.alexdev.minilogbook.domain.model.BgEntry
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BgRepositoryImplTest {
    private val dao: BgEntryDao = mockk(relaxed = true)
    private val repository = BgRepositoryImpl(db = dao)

    @Test
    fun `given domain entry when addBgEntry is called then dao insert is called with mapped entity`() =
        runTest {
            val entry =
                BgEntry(
                    id = 11L,
                    valueInMmol = 6.4,
                    timestamp = 1_717_000_000_000L,
                )
            repository.addBgEntry(entry)
            coVerify(exactly = 1) {
                dao.insertBgEntry(
                    withArg { entity ->
                        assertEquals(entry.id, entity.id)
                        assertEquals(entry.valueInMmol, entity.valueInMmol)
                        assertEquals(entry.timestamp, entity.timestamp)
                    },
                )
            }
        }

    @Test
    fun `given dao entities flow when getBgEntries is collected then returns mapped domain list`() =
        runTest {
            val daoEntries =
                listOf(
                    BgEntryEntity(id = 1, valueInMmol = 5.5, timestamp = 1000L),
                    BgEntryEntity(id = 2, valueInMmol = 7.2, timestamp = 2000L),
                )
            every { dao.getBgEntries() } returns flowOf(daoEntries)
            val result = repository.getBgEntries()
            val emitted = mutableListOf<List<BgEntry>>()
            result.collect { emitted.add(it) }
            assertEquals(1, emitted.size)
            assertEquals(2, emitted.first().size)
            assertEquals(1L, emitted.first()[0].id)
            assertEquals(5.5, emitted.first()[0].valueInMmol)
            assertEquals(1000L, emitted.first()[0].timestamp)
            assertEquals(2L, emitted.first()[1].id)
            assertEquals(7.2, emitted.first()[1].valueInMmol)
            assertEquals(2000L, emitted.first()[1].timestamp)
        }
}
