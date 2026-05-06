package com.example.najmedinezahra

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.najmedinezahra.ui.screens.MainMenuScreen
import com.example.najmedinezahra.ui.screens.SplashScreen
import com.example.najmedinezahra.ui.theme.NajmedineZahraTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented tests for UI components
 * Tests run on Android device/emulator
 */
@RunWith(AndroidJUnit4::class)
class UIComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSplashScreenDisplayed() {
        composeTestRule.setContent {
            NajmedineZahraTheme {
                Surface {
                    SplashScreen()
                }
            }
        }

        // Verify splash screen elements are displayed
        composeTestRule
            .onNodeWithText("Tunisia Heritage Quest")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Discover & Learn")
            .assertIsDisplayed()
    }

    @Test
    fun testMainMenuScreenDisplayed() {
        composeTestRule.setContent {
            NajmedineZahraTheme {
                Surface {
                    MainMenuScreen(
                        onPlayClick = {},
                        onRulesClick = {},
                        onStatsClick = {}
                    )
                }
            }
        }

        // Verify menu buttons are displayed
        composeTestRule
            .onNodeWithText("▶ Play Game")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("📖 How to Play")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("📊 Statistics")
            .assertIsDisplayed()
    }

    @Test
    fun testMainMenuPlayButtonClickable() {
        var playClicked = false

        composeTestRule.setContent {
            NajmedineZahraTheme {
                Surface {
                    MainMenuScreen(
                        onPlayClick = { playClicked = true },
                        onRulesClick = {},
                        onStatsClick = {}
                    )
                }
            }
        }

        // Click play button
        composeTestRule
            .onNodeWithText("▶ Play Game")
            .performClick()

        // Verify callback was triggered
        assert(playClicked)
    }

    @Test
    fun testMainMenuHasAllOptions() {
        composeTestRule.setContent {
            NajmedineZahraTheme {
                Surface {
                    MainMenuScreen(
                        onPlayClick = {},
                        onRulesClick = {},
                        onStatsClick = {}
                    )
                }
            }
        }

        // Verify all three buttons exist
        composeTestRule.onNodeWithText("▶ Play Game").assertIsDisplayed()
        composeTestRule.onNodeWithText("📖 How to Play").assertIsDisplayed()
        composeTestRule.onNodeWithText("📊 Statistics").assertIsDisplayed()
    }
}

