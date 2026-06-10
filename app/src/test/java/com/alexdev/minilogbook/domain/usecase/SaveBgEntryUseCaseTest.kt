package com.alexdev.minilogbook.domain.usecase

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.repository.BgRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SaveBgEntryUseCaseTest {
    private val repository: BgRepository = mockk(relaxed = true)
    private val useCase = SaveBgEntryUseCase(repository = repository)

    @Test
    fun `GIVEN bg entry WHEN invoke is called THEN delegates to repository addBgEntry`() =
        runTest {
            val entry = BgEntry(id = 1, valueInMmol = 6.8, timestamp = 1_717_000_000_000L)
            coEvery { repository.addBgEntry(entry) } returns Unit
            useCase(entry)
            coVerify(exactly = 1) { repository.addBgEntry(entry) }
        }
}
