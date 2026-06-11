package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BgValueTextFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bgValueTextField_renders_value_and_unit_label() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "5.5",
                    unitLabel = "mmol/L",
                    isError = false,
                    onValueChange = {},
                )
            }
        }

        composeTestRule.onNodeWithText("5.5").assertIsDisplayed()
        composeTestRule.onNodeWithText("mmol/L").assertIsDisplayed()
    }

    @Test
    fun bgValueTextField_shows_error_state_on_invalid_input() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "-1",
                    unitLabel = "mmol/L",
                    isError = true,
                    onValueChange = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("-1")
            .assertIsDisplayed()
            .assert(SemanticsMatcher.expectValue(key = SemanticsProperties.Error, expectedValue = "Invalid input"))
    }

    @Test
    fun bgValueTextField_triggers_onValueChange_on_input() {
        var emitted = ""
        composeTestRule.setContent {
            MiniLogbookTheme {
                BgValueTextField(
                    value = "",
                    unitLabel = "mmol/L",
                    isError = false,
                    onValueChange = { emitted = it },
                )
            }
        }

        composeTestRule
            .onNodeWithText("mmol/L")
            .onParent()
            .onChildren()
            .filterToOne(hasSetTextAction())
            .performTextInput("5.5")

        assertEquals("5.5", emitted)
    }
}
