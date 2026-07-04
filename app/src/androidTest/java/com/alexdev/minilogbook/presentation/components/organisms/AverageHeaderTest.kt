package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.domain.model.BgUnit
import com.alexdev.minilogbook.domain.model.labelRes
import com.alexdev.minilogbook.ui.theme.MiniLogbookTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AverageHeaderTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun averageHeader_renders_average_with_mmol_unit() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(averageDisplay = "5.50", averageMmol = 5.50, unit = BgUnit.MMOL)
            }
        }

        composeTestRule.onNodeWithText("5.50").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(BgUnit.MMOL.labelRes())).assertIsDisplayed()
    }

    @Test
    fun averageHeader_renders_average_with_mgdl_unit() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(averageDisplay = "99.10", averageMmol = 6.77, unit = BgUnit.MGDL)
            }
        }

        composeTestRule.onNodeWithText("99.10").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(BgUnit.MGDL.labelRes())).assertIsDisplayed()
    }

    @Test
    fun averageHeader_renders_em_dash_when_no_entries() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(averageDisplay = "-", averageMmol = null, unit = BgUnit.MMOL)
            }
        }

        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun averageHeader_emptyState_noBadge() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(
                    averageDisplay = "—",
                    averageMmol = null,
                    unit = BgUnit.MMOL,
                )
            }
        }
        composeTestRule.onNodeWithText("Within target").assertDoesNotExist()
        composeTestRule.onNodeWithText("Below target").assertDoesNotExist()
        composeTestRule.onNodeWithText("Above target").assertDoesNotExist()
        composeTestRule.onNodeWithText("High").assertDoesNotExist()
    }

    @Test
    fun averageHeader_normalValue_showsWithinTargetBadge() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(
                    averageDisplay = "5.50",
                    averageMmol = 5.5,
                    unit = BgUnit.MMOL,
                )
            }
        }
        composeTestRule.onNodeWithText("Within target").assertIsDisplayed()
    }

    @Test
    fun averageHeader_highValue_showsHighBadge() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(
                    averageDisplay = "11.00",
                    averageMmol = 11.0,
                    unit = BgUnit.MMOL,
                )
            }
        }
        composeTestRule.onNodeWithText("High").assertIsDisplayed()
    }

    @Test
    fun averageHeader_hasAccessibilityContentDescription() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(
                    averageDisplay = "5.50",
                    averageMmol = 5.5,
                    unit = BgUnit.MMOL,
                )
            }
        }
        composeTestRule.onNode(hasContentDescription("5.50", substring = true)).assertIsDisplayed()
    }
}
