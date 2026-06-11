package com.alexdev.minilogbook.presentation.components.organisms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexdev.minilogbook.domain.model.BgUnit
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
                AverageHeader(average = "5.50", unit = BgUnit.MMOL)
            }
        }

        composeTestRule.onNodeWithText("Your average is 5.50 mmol/L").assertIsDisplayed()
    }

    @Test
    fun averageHeader_renders_average_with_mgdl_unit() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(average = "122.00", unit = BgUnit.MGDL)
            }
        }

        composeTestRule.onNodeWithText("Your average is 122.00 mg/dL").assertIsDisplayed()
    }

    @Test
    fun averageHeader_renders_em_dash_when_no_entries() {
        composeTestRule.setContent {
            MiniLogbookTheme {
                AverageHeader(average = "—", unit = BgUnit.MMOL)
            }
        }

        composeTestRule.onNodeWithText("Your average is — mmol/L").assertIsDisplayed()
    }
}
