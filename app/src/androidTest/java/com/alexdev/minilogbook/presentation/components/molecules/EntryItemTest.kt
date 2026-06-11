package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.domain.model.BgEntry
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EntryItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun entryItem_renders_value_in_mmol() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                EntryItem(
                    entry = BgEntry(id = 1, valueInMmol = 5.5, timestamp = System.currentTimeMillis()),
                    unit = BgUnit.MMOL,
                )
            }
        }

        composeTestRule.onNodeWithText("5.50 mmol/L").assertIsDisplayed()
    }

    @Test
    fun entryItem_renders_converted_value_in_mgdl() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                EntryItem(
                    entry = BgEntry(id = 1, valueInMmol = 5.5, timestamp = System.currentTimeMillis()),
                    unit = BgUnit.MGDL,
                )
            }
        }

        composeTestRule.onNodeWithText("99.10 mg/dL").assertIsDisplayed()
    }
}
