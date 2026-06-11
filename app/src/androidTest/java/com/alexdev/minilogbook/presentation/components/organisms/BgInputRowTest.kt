package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BgInputRowTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bgInputRow_save_button_is_disabled_when_input_is_empty() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgInputRow(
                    value = "",
                    unitLabel = "mmol/L",
                    error = null,
                    onValueChange = {},
                    onSave = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Save").assertIsNotEnabled()
    }

    @Test
    fun bgInputRow_save_button_is_disabled_when_error_is_present() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgInputRow(
                    value = "-1",
                    unitLabel = "mmol/L",
                    error = "Must be >= 2.8 mmol/L",
                    onValueChange = {},
                    onSave = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Save").assertIsNotEnabled()
    }

    @Test
    fun bgInputRow_save_button_is_enabled_with_valid_input() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgInputRow(
                    value = "5.5",
                    unitLabel = "mmol/L",
                    error = null,
                    onValueChange = {},
                    onSave = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Save").assertIsEnabled()
    }

    @Test
    fun bgInputRow_triggers_onSave_when_save_button_clicked() {
        var saved = false
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgInputRow(
                    value = "5.5",
                    unitLabel = "mmol/L",
                    error = null,
                    onValueChange = {},
                    onSave = { saved = true },
                )
            }
        }

        composeTestRule.onNodeWithText("Save").performClick()
        assertTrue(saved)
    }

    @Test
    fun bgInputRow_error_message_is_displayed() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgInputRow(
                    value = "-1",
                    unitLabel = "mmol/L",
                    error = "Must be >= 2.8 mmol/L",
                    onValueChange = {},
                    onSave = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Must be >= 2.8 mmol/L").assertIsDisplayed()
    }
}
