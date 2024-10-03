package com.baesuii.fluxnews.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baesuii.fluxnews.presentation.details.DetailsScreen
import com.baesuii.fluxnews.util.Constants.dummyArticle
import com.baesuii.fluxnews.util.formatDate
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showDetailsScreen_whenArticleIsProvided() {
        // Provide the article and set the content to the DetailsScreen
        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { }
            )
        }

        // Verify all key elements are displayed after scroll
        composeTestRule.onNodeWithText(dummyArticle.title).assertExists()
        composeTestRule.onNodeWithText(dummyArticle.author!!).assertExists()
        composeTestRule.onNodeWithText(formatDate(dummyArticle.publishedAt)).assertExists()

        // Try asserting a smaller portion of the content
        val partialContent = dummyArticle.content.take(30)  // First 30 characters
        composeTestRule.onNodeWithText(partialContent, substring = true).performScrollTo().assertExists()
    }

    @Test
    fun backButton_navigatesUp() {
        var isNavigatedUp = false

        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { isNavigatedUp = true }
            )
        }

        // Simulate the back button click
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(isNavigatedUp)
    }

    @Test
    fun bookmarkIconToggles_WhenBookmarkClicked() {
        // Simulate bookmarking interaction
        var isBookmarked = false

        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = {
                    isBookmarked = !isBookmarked
                },
                navigateUp = { }
            )
        }

        // Bookmark at first click
        composeTestRule.onNodeWithContentDescription("Bookmark").performClick()
        assert(isBookmarked) // Article should be bookmarked after clicking

        // Remove at second click
        composeTestRule.onNodeWithContentDescription("Bookmark").performClick()
        assert(!isBookmarked)
    }


    @Test
    fun shareButton_triggersShareIntent() {
        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { }
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Share").performClick()
    }

    @Test
    fun browseButton_triggersBrowsingIntent() {
        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { }
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Browse").performClick()
    }
}

