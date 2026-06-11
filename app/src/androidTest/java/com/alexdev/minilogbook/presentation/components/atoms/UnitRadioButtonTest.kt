package com.alexdev.minilogbook.presentation.components.atoms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isNotSelected
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UnitRadioButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun unitRadioButton_renders_unit_label() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MMOL, selected = true, onSelected = {})
            }
        }
        composeTestRule.onNodeWithText("mmol/L").assertIsDisplayed()
    }

    @Test
    fun unitRadioButton_radio_button_is_selected() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MMOL, selected = true, onSelected = {})
            }
        }

        composeTestRule.onNode(isSelectable() and isSelected()).assertIsDisplayed()
    }

    @Test
    fun unitRadioButton_radio_button_is_not_selected() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MGDL, selected = false, onSelected = {})
            }
        }

        composeTestRule.onNode(isSelectable() and isNotSelected()).assertIsDisplayed()
    }

    @Test
    fun unitRadioButton_triggers_onSelected_when_clicked() {
        var clicked = false
        composeTestRule.setContent {
            MiniLogbookTheme {
                UnitRadioButton(unit = BgUnit.MMOL, selected = false, onSelected = { clicked = true })
            }
        }

        composeTestRule.onNodeWithText("mmol/L").performClick()
        assertTrue(clicked)
    }
}
