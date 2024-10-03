package com.baesuii.fluxnews.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baesuii.fluxnews.presentation.onboarding.OnboardingEvent
import com.baesuii.fluxnews.presentation.onboarding.OnboardingScreen
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun verifyInitialPageIsDisplayed() {
        composeTestRule.setContent {
            FluxNewsTheme {
                OnboardingScreen()
            }
        }

        // text or image of page 1 is displayed
        composeTestRule.onNodeWithText("Explore").assertIsDisplayed()
    }

    @Test
    fun navigateToNextPage_whenNextButtonClicked() {
        composeTestRule.setContent {
            FluxNewsTheme {
                OnboardingScreen()
            }
        }

        // Click page 1 button
        composeTestRule.onNodeWithText("Explore").performClick()

        // Verify page 2 is displayed
        composeTestRule.onNodeWithText("Next").assertIsDisplayed()
    }

    @Test
    fun navigateToPreviousPage_whenBackButtonClicked() {
        composeTestRule.setContent {
            FluxNewsTheme {
                OnboardingScreen()
            }
        }

        // Navigate to page 2 first
        composeTestRule.onNodeWithText("Explore").performClick()

        // Now verify that "Back" is displayed and can navigate back
        composeTestRule.onNodeWithText("Back").performClick()

        // Verify we are back on page 1
        composeTestRule.onNodeWithText("Explore").assertIsDisplayed()
    }

    @Test
    fun verifyGetStartedTriggersEvent_onLastPage() = runBlocking {
        var saveAppEntryEventTriggered = false

        composeTestRule.setContent {
            FluxNewsTheme {
                OnboardingScreen(
                    event = { event ->
                        if (event is OnboardingEvent.SaveAppEntry) {
                            saveAppEntryEventTriggered = true
                        }
                    }
                )
            }
        }

        // Navigate to all pages
        composeTestRule.onNodeWithText("Explore").performClick()
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.onNodeWithText("Get Started").performClick()

        assert(saveAppEntryEventTriggered)
    }
}
