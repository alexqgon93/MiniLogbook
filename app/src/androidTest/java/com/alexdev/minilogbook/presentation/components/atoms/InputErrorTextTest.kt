package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InputErrorTextTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun inputErrorText_error_message_is_visible_when_error_is_not_null() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                InputErrorText(error = "Must be >= 2.80 mmol/L")
            }
        }

        composeTestRule.onNodeWithText("Must be >= 2.80 mmol/L").assertIsDisplayed()
    }

    @Test
    fun inputErrorText_error_message_is_not_visible_when_error_is_null() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                InputErrorText(error = null)
            }
        }
        composeTestRule.onNodeWithText("Must be >= 2.80 mmol/L").assertDoesNotExist()
    }
}
