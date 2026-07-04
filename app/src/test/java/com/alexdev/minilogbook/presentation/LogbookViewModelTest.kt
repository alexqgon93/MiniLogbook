package com.alexdev.minilogbook.presentation

import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.time.TimeProvider
import com.alexdev.minilogbook.domain.usecase.GetBgEntriesUseCase
import com.alexdev.minilogbook.domain.usecase.SaveBgEntryUseCase
import com.alexdev.minilogbook.presentation.LogbookInputError.AboveMax
import com.alexdev.minilogbook.presentation.LogbookInputError.BelowMin
import com.alexdev.minilogbook.presentation.LogbookInputError.InvalidNumber
import com.alexdev.minilogbook.presentation.LogbookInputError.Required
import com.alexdev.minilogbook.presentation.LogbookInputError.SaveFailed
import com.alexdev.minilogbook.presentation.utils.BgValueFormatter
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LogbookViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getBgEntriesUseCase: GetBgEntriesUseCase = mockk()
    private val saveBgEntryUseCase: SaveBgEntryUseCase = mockk(relaxed = true)
    private val bgValueFormatter = BgValueFormatter()
    private val timeProvider: TimeProvider = mockk()

    private lateinit var viewModel: LogbookViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { getBgEntriesUseCase() } returns flowOf(emptyList())
        every { timeProvider.now() } returns 1_717_000_000_000L
        viewModel = LogbookViewModel(getBgEntriesUseCase, saveBgEntryUseCase, bgValueFormatter, timeProvider)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Nested
    inner class InitialState {
        @Test
        fun `GIVEN viewModel created WHEN no events fired THEN default state is correct`() =
            runTest {
                val state = viewModel.uiState.value
                state.selectedUnit shouldBe BgUnit.MMOL
                state.inputValue shouldBe ""
                state.inputError.shouldBeNull()
                state.entries shouldBe emptyList()
            }
    }

    @Nested
    inner class InputValidation {
        @Test
        fun `GIVEN empty input WHEN handleInputChange THEN no error shown`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue(""))
                viewModel.uiState.value.inputError
                    .shouldBeNull()
            }

        @Test
        fun `GIVEN non numeric input WHEN handleInputChange THEN invalid number error`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("abc"))
                viewModel.uiState.value.inputError shouldBe InvalidNumber
            }

        @Test
        fun `GIVEN value below min WHEN handleInputChange THEN exact below min error shown`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("1.0"))
                viewModel.uiState.value.inputError shouldBe BelowMin(2.8, BgUnit.MMOL)
            }

        @Test
        fun `GIVEN value above max WHEN handleInputChange THEN exact above max error shown`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("999.0"))
                viewModel.uiState.value.inputError shouldBe AboveMax(33.3, BgUnit.MMOL)
            }

        @Test
        fun `GIVEN value equals min WHEN handleInputChange THEN no error shown`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("2.8"))
                viewModel.uiState.value.inputError
                    .shouldBeNull()
            }

        @Test
        fun `GIVEN value equals max WHEN handleInputChange THEN no error shown`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("33.3"))
                viewModel.uiState.value.inputError
                    .shouldBeNull()
            }

        @Test
        fun `GIVEN valid value WHEN handleInputChange THEN no error and input updated`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("5.5"))
                viewModel.uiState.value.inputError
                    .shouldBeNull()
                viewModel.uiState.value.inputValue shouldBe "5.5"
            }
    }

    @Nested
    inner class UnitChange {
        @Test
        fun `GIVEN mmol input WHEN unit changes to mgdl THEN input is converted`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("5.0"))
                viewModel.onHandleUiEvent(LogbookUiEvent.ChangeUnit(BgUnit.MGDL))
                viewModel.uiState.value.inputValue shouldBe "90.09"
            }

        @Test
        fun `GIVEN mgdl unit WHEN unit changes back to mmol THEN input is converted back`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("5.0"))
                viewModel.onHandleUiEvent(LogbookUiEvent.ChangeUnit(BgUnit.MGDL))
                viewModel.onHandleUiEvent(LogbookUiEvent.ChangeUnit(BgUnit.MMOL))
                viewModel.uiState.value.inputValue shouldBe "5.00"
            }

        @Test
        fun `GIVEN empty input WHEN unit changes THEN input stays empty`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.ChangeUnit(BgUnit.MGDL))
                viewModel.uiState.value.inputValue shouldBe ""
            }

        @Test
        fun `GIVEN entries exist WHEN unit changes to mgdl THEN average recomputed in mgdl`() =
            runTest {
                every { getBgEntriesUseCase() } returns
                    flowOf(
                        listOf(BgEntry(valueInMmol = 5.0, timestamp = 1000L)),
                    )
                viewModel =
                    LogbookViewModel(getBgEntriesUseCase, saveBgEntryUseCase, bgValueFormatter, timeProvider)
                advanceUntilIdle()

                viewModel.onHandleUiEvent(LogbookUiEvent.ChangeUnit(BgUnit.MGDL))
                viewModel.uiState.value.averageDisplay shouldBe "90.09"
            }

        @Test
        fun `GIVEN mgdl unit selected WHEN entries collected THEN average displayed in mgdl`() =
            runTest {
                every { getBgEntriesUseCase() } returns
                    flowOf(
                        listOf(BgEntry(valueInMmol = 5.0, timestamp = 1000L)),
                    )
                viewModel =
                    LogbookViewModel(getBgEntriesUseCase, saveBgEntryUseCase, bgValueFormatter, timeProvider)
                viewModel.onHandleUiEvent(LogbookUiEvent.ChangeUnit(BgUnit.MGDL))
                advanceUntilIdle()

                viewModel.uiState.value.averageDisplay shouldBe "90.09"
            }
    }

    @Nested
    inner class Save {
        @Test
        fun `GIVEN blank input WHEN handleSave THEN error shown and save not called`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.SaveReading)
                viewModel.uiState.value.inputError shouldBe Required
                coVerify(exactly = 0) { saveBgEntryUseCase(any()) }
            }

        @Test
        fun `GIVEN invalid input WHEN handleSave THEN invalid number error shown`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("abc"))
                viewModel.onHandleUiEvent(LogbookUiEvent.SaveReading)
                viewModel.uiState.value.inputError shouldBe InvalidNumber
            }

        @Test
        fun `GIVEN valid input WHEN handleSave THEN save delegated and input cleared`() =
            runTest {
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("6.5"))
                viewModel.onHandleUiEvent(LogbookUiEvent.SaveReading)
                advanceUntilIdle()

                coVerify(exactly = 1) { saveBgEntryUseCase(any()) }
                viewModel.uiState.value.inputValue shouldBe ""
                viewModel.uiState.value.inputError
                    .shouldBeNull()
            }

        @Test
        fun `GIVEN save fails WHEN handleSave THEN save failed error shown`() =
            runTest {
                coEvery { saveBgEntryUseCase(any()) } throws RuntimeException("DB error")
                viewModel.onHandleUiEvent(LogbookUiEvent.UpdateInputValue("6.5"))
                viewModel.onHandleUiEvent(LogbookUiEvent.SaveReading)
                advanceUntilIdle()

                viewModel.uiState.value.inputError shouldBe SaveFailed
            }
    }

    @Nested
    inner class Average {
        @Test
        fun `GIVEN empty entries WHEN collected THEN average shows dash`() =
            runTest {
                advanceUntilIdle()
                viewModel.uiState.value.averageDisplay shouldBe "—"
            }

        @Test
        fun `GIVEN single entry WHEN collected THEN average equals entry value`() =
            runTest {
                every { getBgEntriesUseCase() } returns
                    flowOf(
                        listOf(BgEntry(valueInMmol = 6.0, timestamp = 1000L)),
                    )
                viewModel = LogbookViewModel(getBgEntriesUseCase, saveBgEntryUseCase, bgValueFormatter, timeProvider)
                advanceUntilIdle()

                viewModel.uiState.value.averageDisplay shouldBe "6.00"
            }

        @Test
        fun `GIVEN multiple entries WHEN collected THEN average computed correctly`() =
            runTest {
                every { getBgEntriesUseCase() } returns
                    flowOf(
                        listOf(
                            BgEntry(valueInMmol = 4.0, timestamp = 1000L),
                            BgEntry(valueInMmol = 8.0, timestamp = 2000L),
                        ),
                    )
                viewModel = LogbookViewModel(getBgEntriesUseCase, saveBgEntryUseCase, bgValueFormatter, timeProvider)
                advanceUntilIdle()

                viewModel.uiState.value.averageDisplay shouldBe "6.00"
            }
    }

    @Nested
    inner class Load {
        @Test
        fun `GIVEN entries load fails WHEN collected THEN load error is shown without input error`() =
            runTest {
                every { getBgEntriesUseCase() } returns
                    flow {
                        throw RuntimeException("DB error")
                    }
                viewModel =
                    LogbookViewModel(
                        getBgEntriesUseCase,
                        saveBgEntryUseCase,
                        bgValueFormatter,
                        timeProvider,
                    )
                advanceUntilIdle()

                viewModel.uiState.value.loadError shouldBe true
                viewModel.uiState.value.inputError
                    .shouldBeNull()
            }

        @Test
        fun `GIVEN entries load fails WHEN retry succeeds THEN load error clears and entries update`() =
            runTest {
                val recoveredEntries = listOf(BgEntry(valueInMmol = 5.0, timestamp = 1000L))
                every { getBgEntriesUseCase() } returnsMany
                    listOf(
                        flow {
                            throw RuntimeException("DB error")
                        },
                        flowOf(recoveredEntries),
                    )
                viewModel =
                    LogbookViewModel(
                        getBgEntriesUseCase,
                        saveBgEntryUseCase,
                        bgValueFormatter,
                        timeProvider,
                    )
                advanceUntilIdle()

                viewModel.onHandleUiEvent(LogbookUiEvent.RetryLoad)
                advanceUntilIdle()

                viewModel.uiState.value.loadError shouldBe false
                viewModel.uiState.value.entries shouldBe recoveredEntries
            }
    }
}
