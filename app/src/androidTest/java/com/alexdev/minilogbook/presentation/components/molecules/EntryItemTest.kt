package com.alexdev.minilogbook.presentation.components.molecules

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
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

    private fun entry(valueMmol: Double) =
        BgEntry(
            id = 1,
            valueInMmol = valueMmol,
            timestamp = System.currentTimeMillis(),
        )

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

        composeTestRule.onNodeWithTag("entry_item_value").assertIsDisplayed().assertTextEquals("5.50")
        composeTestRule.onNodeWithTag("entry_item_unit").assertIsDisplayed().assertTextEquals("mmol/L")
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

        composeTestRule.onNodeWithTag("entry_item_value").assertIsDisplayed().assertTextEquals("99.10")
        composeTestRule.onNodeWithTag("entry_item_unit").assertIsDisplayed().assertTextEquals("mg/dL")
    }

    @Test
    fun entryItem_normalRange_showsWithinTargetBadge() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                EntryItem(entry = entry(valueMmol = 5.5), unit = BgUnit.MMOL)
            }
        }
        composeTestRule.onNodeWithText("Within target").assertIsDisplayed()
    }

    @Test
    fun entryItem_lowValue_showsBelowTargetBadge() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                EntryItem(entry = entry(valueMmol = 2.0), unit = BgUnit.MMOL)
            }
        }
        composeTestRule.onNodeWithText("Below target").assertIsDisplayed()
    }

    @Test
    fun entryItem_elevatedValue_showsAboveTargetBadge() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                EntryItem(entry = entry(valueMmol = 8.5), unit = BgUnit.MMOL)
            }
        }
        composeTestRule.onNodeWithText("Above target").assertIsDisplayed()
    }

    @Test
    fun entryItem_highValue_showsHighBadge() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                EntryItem(entry = entry(valueMmol = 11.0), unit = BgUnit.MMOL)
            }
        }
        composeTestRule.onNodeWithText("High").assertIsDisplayed()
    }

    @Test
    fun entryItem_isCardElevated() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                EntryItem(entry = entry(valueMmol = 5.5), unit = BgUnit.MMOL)
            }
        }

        composeTestRule
            .onNodeWithText("5.50")
            .assertIsDisplayed()
            .onParent()
            .onParent()
            .assertExists()
    }
}
