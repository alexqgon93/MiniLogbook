package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UnitSelectorTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun unitSelector_renders_all_units() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                UnitSelector(selected = BgUnit.MMOL, onUnitSelected = {})
            }
        }

        composeTestRule.onNodeWithText("mmol/L").assertIsDisplayed()
        composeTestRule.onNodeWithText("mg/dL").assertIsDisplayed()
    }

    @Test
    fun unitSelector_triggers_correct_unit_on_selection() {
        var selected: BgUnit? = null
        composeTestRule.setContent {
            MiniLogbookTheme {
                UnitSelector(selected = BgUnit.MMOL, onUnitSelected = { selected = it })
            }
        }

        composeTestRule.onNodeWithText("mg/dL").performClick()
        assertEquals(BgUnit.MGDL, selected)
    }
}
