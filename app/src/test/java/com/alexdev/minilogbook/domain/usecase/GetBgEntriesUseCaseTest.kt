package com.alexdev.minilogbook.domain.usecase

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.repository.BgRepository
import com.alexdev.minilogbook.testing.ReadingFixtures
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
            val entries =
                ReadingFixtures.readings(
                    5.5,
                    7.1,
                )
            every { repository.getBgEntries(limit = any()) } returns flowOf(entries)
            val emitted = useCase().first()
            assertEquals(entries, emitted)
        }

    @Test
    fun `GIVEN custom limit WHEN invoke is called THEN delegates limit to repository`() =
        runTest {
            every { repository.getBgEntries(limit = 25) } returns flowOf(emptyList())

            val emitted = useCase(limit = 25).first()

            assertEquals(emptyList<BgEntry>(), emitted)
        }
}
