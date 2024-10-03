package com.baesuii.fluxnews.base

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.baesuii.fluxnews.presentation.navgraph.NewsNavGraph
import com.baesuii.fluxnews.presentation.navgraph.NewsRouter
import com.baesuii.fluxnews.presentation.onboarding.OnboardingScreen
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavHostTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNewsNavHost() {
        hiltRule.inject()

        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NewsNavGraph(startDestination = NewsRouter.NewsNavigation.route)
        }

    }

    @Test
    fun verifyOnboardingScreenDisplayedAtStart() {
        composeTestRule.onNodeWithTag(ONBOARDING_SCREEN)
            .assertExists()
    }

    @Test
    fun navigateToHomeScreen_fromOnboardingScreen() {
        composeTestRule.setContent {
            FluxNewsTheme {
                OnboardingScreen()
            }
        }

        composeTestRule.onNodeWithTag(ONBOARDING_SCREEN)
            .performClick()

        composeTestRule.onNodeWithTag(HOME_SCREEN)
            .assertExists()
    }

    @Test
    fun navigateToExploreScreen_fromHomeScreen() {

        composeTestRule.onNodeWithTag(SEARCH_ICON)
            .performClick()

        composeTestRule.onNodeWithTag(EXPLORE_SCREEN)
            .assertExists()
    }

    @Test
    fun navigateToBookmarkScreen_fromExploreScreen() {
        composeTestRule.onNodeWithTag(BOOKMARK_ICON)
            .performClick()

        composeTestRule.onNodeWithTag(BOOKMARK_SCREEN)
            .assertExists()
    }

    @Test
    fun navigateBackToHomeScreen_fromBookmarkScreen() {
        composeTestRule.onNodeWithTag(BACK_ICON)
            .performClick()

        composeTestRule.onNodeWithTag(HOME_SCREEN)
            .assertExists()
    }

    @Test
    fun navigateToSettingsScreen_fromHomeScreen() {
        composeTestRule.onNodeWithTag(SETTINGS_ICON)
            .performClick()

        composeTestRule.onNodeWithTag(SETTINGS_SCREEN)
            .assertExists()
    }

    @Test
    fun verifyTimezoneDialogDisplayed_onSettingsScreen() {
        composeTestRule.onNodeWithTag(SETTINGS_SCREEN)
            .performClick()

        composeTestRule.onNodeWithTag(TIMEZONE_BUTTON)
            .performClick()

        composeTestRule.onNodeWithTag(TIMEZONE_DIALOG)
            .assertExists()
    }

    companion object TestTags {
        const val ONBOARDING_SCREEN = "OnboardingScreen"
        const val HOME_SCREEN = "HomeScreen"
        const val EXPLORE_SCREEN = "ExploreScreen"
        const val BOOKMARK_SCREEN = "BookmarkScreen"
        const val SETTINGS_SCREEN = "SettingsScreen"
        const val SEARCH_ICON = "SearchIcon"
        const val SETTINGS_ICON = "SettingsIcon"
        const val BOOKMARK_ICON = "BookmarkIcon"
        const val TIMEZONE_BUTTON = "TimezoneButton"
        const val TIMEZONE_DIALOG = "TimezoneDialog"
        const val BACK_ICON = "BackIcon"
    }
}


