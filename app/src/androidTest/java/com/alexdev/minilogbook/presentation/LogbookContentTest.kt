package com.alexdev.minilogbook.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.label
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class LogbookContentTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private fun averageText(
        value: String,
        unit: BgUnit,
    ) = "Your average is $value ${unit.label()}"

    private fun saveText() = "Save"

    private fun entry(
        id: Long,
        valueInMmol: Double,
    ) = BgEntry(
        id = id,
        valueInMmol = valueInMmol,
        timestamp = System.currentTimeMillis(),
    )

    @Test
    fun emptyState_showsDashAverage() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }

        composeRule.onNodeWithText(averageText("—", BgUnit.MMOL)).assertIsDisplayed()
    }

    @Test
    fun emptyState_saveButtonIsDisabled() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }

        composeRule.onNodeWithText(saveText()).assertIsNotEnabled()
    }

    @Test
    fun emptyState_bothUnitsAreVisible() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }

        composeRule.onAllNodesWithText(BgUnit.MMOL.label()).onFirst().assertIsDisplayed()
        composeRule.onAllNodesWithText(BgUnit.MGDL.label()).onFirst().assertIsDisplayed()
    }

    @Test
    fun unitSelection_mmolIsSelectedByDefault() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }

        composeRule.onNode(isSelectable() and isSelected()).assertIsDisplayed()
    }

    @Test
    fun unitSelection_clickingMgDlEmitsChangeUnitIntent() {
        val intents = mutableListOf<LogbookUiEvent>()

        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state = LogbookUiState(selectedUnit = BgUnit.MMOL),
                    onEvent = { intents.add(it) },
                )
            }
        }

        composeRule.onNodeWithText(BgUnit.MGDL.label()).performClick()
        assertEquals(listOf(LogbookUiEvent.ChangeUnit(BgUnit.MGDL)), intents)
    }

    @Test
    fun unitSelection_clickingMmolEmitsChangeUnitIntent() {
        val intents = mutableListOf<LogbookUiEvent>()

        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state = LogbookUiState(selectedUnit = BgUnit.MGDL),
                    onEvent = { intents.add(it) },
                )
            }
        }

        composeRule.onNodeWithText(BgUnit.MMOL.label()).performClick()
        assertEquals(listOf(LogbookUiEvent.ChangeUnit(BgUnit.MMOL)), intents)
    }

    @Test
    fun input_saveButtonEnabledWithValidValue() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state = LogbookUiState(inputValue = "5.5", inputError = null),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText(saveText()).assertIsEnabled()
    }

    @Test
    fun input_saveButtonDisabledWhenErrorPresent() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            inputValue = "-1",
                            inputError = "Must be >= 2.8 mmol/L",
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText(saveText()).assertIsNotEnabled()
    }

    @Test
    fun input_errorMessageIsDisplayed() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            inputValue = "-1",
                            inputError = "Must be >= 2.8 mmol/L",
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText("Must be >= 2.8 mmol/L").assertIsDisplayed()
    }

    @Test
    fun input_clickingSaveEmitsSaveIntent() {
        val intents = mutableListOf<LogbookUiEvent>()

        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state = LogbookUiState(inputValue = "5.5", inputError = null),
                    onEvent = { intents.add(it) },
                )
            }
        }

        composeRule.onNodeWithText(saveText()).performClick()
        assertEquals(listOf(LogbookUiEvent.SaveReading), intents)
    }

    @Test
    fun average_displaysCorrectlyInMmol() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            selectedUnit = BgUnit.MMOL,
                            averageDisplay = "5.50",
                            entries = listOf(entry(1, 5.5)),
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText(averageText("5.50", BgUnit.MMOL)).assertIsDisplayed()
    }

    @Test
    fun average_displaysCorrectlyInMgDl() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            selectedUnit = BgUnit.MGDL,
                            averageDisplay = "99.10",
                            entries = listOf(entry(1, 5.5)),
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText(averageText("99.10", BgUnit.MGDL)).assertIsDisplayed()
    }

    @Test
    fun entriesList_displaysAllEntries() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            selectedUnit = BgUnit.MMOL,
                            entries =
                                listOf(
                                    entry(id = 1, valueInMmol = 5.5),
                                    entry(id = 2, valueInMmol = 6.2),
                                ),
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText("5.50 mmol/L").assertIsDisplayed()
        composeRule.onNodeWithText("6.20 mmol/L").assertIsDisplayed()
    }

    @Test
    fun entriesList_emptyListShowsNoEntryNodes() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state = LogbookUiState(entries = emptyList()),
                    onEvent = {},
                )
            }
        }

        composeRule.onAllNodesWithText(BgUnit.MMOL.label(), useUnmergedTree = true).onFirst().assertIsDisplayed()
        composeRule.onNodeWithText("5.50 mmol/L").assertDoesNotExist()
    }
}
