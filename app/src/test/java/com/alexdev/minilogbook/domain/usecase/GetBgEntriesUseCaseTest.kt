package com.alexdev.minilogbook.domain.usecase

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.repository.BgRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetBgEntriesUseCaseTest {
    private val repository: BgRepository = mockk(relaxed = true)
    private val useCase = GetBgEntriesUseCase(repository = repository)

    @Test
    fun `GIVEN repository entries flow WHEN invoke is called THEN returns same emitted entries`() =
        runTest {
            val entries = listOf(
                BgEntry(id = 1L, valueInMmol = 5.5, timestamp = 1000L),
                BgEntry(id = 2L, valueInMmol = 7.1, timestamp = 2000L)
            )
            every { repository.getBgEntries() } returns flowOf(entries)
            val emitted = useCase().first()
            assertEquals(entries, emitted)
        }
}
