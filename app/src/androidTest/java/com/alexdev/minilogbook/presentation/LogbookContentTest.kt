package com.alexdev.minilogbook.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.presentation.utils.labelRes
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class LogbookContentTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun emptyState_showsDashAverage() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }

        composeRule.onNodeWithText("—").assertIsDisplayed()
    }

    @Test
    fun emptyState_saveButtonIsDisabled() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }

        composeRule.onNodeWithText(SAVE_BUTTON_TEXT).assertIsNotEnabled()
    }

    @Test
    fun emptyState_bothUnitsAreVisible() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(state = LogbookUiState(), onEvent = {})
            }
        }

        composeRule
            .onAllNodesWithText(composeRule.activity.getString(BgUnit.MMOL.labelRes()))
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(composeRule.activity.getString(BgUnit.MGDL.labelRes()))
            .onFirst()
            .assertIsDisplayed()
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

        composeRule.onNodeWithText(composeRule.activity.getString(BgUnit.MGDL.labelRes())).performClick()
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

        composeRule.onNodeWithText(composeRule.activity.getString(BgUnit.MMOL.labelRes())).performClick()
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

        composeRule.onNodeWithText(SAVE_BUTTON_TEXT).assertIsEnabled()
    }

    @Test
    fun input_saveButtonDisabledWhenErrorPresent() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            inputValue = "-1",
                            inputError = LogbookInputError.BelowMin(2.8, BgUnit.MMOL),
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText(SAVE_BUTTON_TEXT).assertIsNotEnabled()
    }

    @Test
    fun input_errorMessageIsDisplayed() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            inputValue = "-1",
                            inputError = LogbookInputError.BelowMin(2.8, BgUnit.MMOL),
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onNodeWithText("Must be >= 2.80 mmol/L").assertIsDisplayed()
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

        composeRule.onNodeWithText(SAVE_BUTTON_TEXT).performClick()
        assertEquals(listOf(LogbookUiEvent.SaveReading), intents)
    }

    @Test
    fun entriesList_showsCorrectStatusBadgePerEntry() {
        composeRule.setContent {
            MiniLogbookTheme {
                LogbookContent(
                    state =
                        LogbookUiState(
                            selectedUnit = BgUnit.MMOL,
                            entries =
                                listOf(
                                    BgEntry(id = 1, valueInMmol = 5.5, timestamp = System.currentTimeMillis()),
                                    BgEntry(id = 2, valueInMmol = 2.0, timestamp = System.currentTimeMillis()),
                                    BgEntry(id = 3, valueInMmol = 8.5, timestamp = System.currentTimeMillis()),
                                    BgEntry(id = 4, valueInMmol = 11.0, timestamp = System.currentTimeMillis()),
                                ),
                        ),
                    onEvent = {},
                )
            }
        }

        composeRule.onAllNodesWithText("Within target").onFirst().assertIsDisplayed()
        composeRule.onAllNodesWithText("Below target").onFirst().assertIsDisplayed()
        composeRule.onAllNodesWithText("Above target").onFirst().assertIsDisplayed()
        composeRule.onNodeWithTag("entry list").performScrollToNode(hasText("High")).assertIsDisplayed()
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

        composeRule
            .onAllNodesWithText(
                composeRule.activity.getString(BgUnit.MMOL.labelRes()),
                useUnmergedTree = true,
            ).onFirst()
            .assertIsDisplayed()
        composeRule.onNodeWithText("5.50 mmol/L").assertDoesNotExist()
    }

    companion object {
        private const val SAVE_BUTTON_TEXT = "Save reading"
    }
}
