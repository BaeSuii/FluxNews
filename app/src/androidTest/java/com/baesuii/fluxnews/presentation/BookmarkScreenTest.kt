package com.baesuii.fluxnews.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source
import com.baesuii.fluxnews.presentation.bookmark.BookmarkScreen
import com.baesuii.fluxnews.presentation.bookmark.BookmarkState
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import org.junit.Rule
import org.junit.Test

class BookmarkScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val noSavedNewsMessage = context.getString(R.string.no_saved_news)

    private val sampleArticle1 = Article(
        author = "Author 1",
        content = "Content 1",
        description = "Description 1",
        publishedAt = "2023-01-01T00:00:00Z",
        source = Source(id = "source1", name = "Source 1"),
        title = "Title 1",
        url = "https://example.com/news1",
        urlToImage = "https://example.com/image1.jpg"
    )

    private val sampleArticle2 = Article(
        author = "Author 2",
        content = "Content 2",
        description = "Description 2",
        publishedAt = "2023-02-01T00:00:00Z",
        source = Source(id = "source2", name = "Source 2"),
        title = "Title 2",
        url = "https://example.com/news2",
        urlToImage = "https://example.com/image2.jpg"
    )

    @Test
    fun showEmptyState_whenBookmarkIsEmpty() {
        composeTestRule.setContent {
            FluxNewsTheme {
                BookmarkScreen(
                    state = BookmarkState(articles = emptyList()),
                    navigateToDetails = {}
                )
            }
        }

        // Check that the message is displayed
        composeTestRule.onNodeWithText(noSavedNewsMessage).assertIsDisplayed()
    }

    @Test
    fun showBookmarks_whenBookmarkIsNotEmpty() {
        val bookmarks = listOf(sampleArticle1, sampleArticle2)

        composeTestRule.setContent {
            FluxNewsTheme {
                BookmarkScreen(
                    state = BookmarkState(articles = bookmarks),
                    navigateToDetails = {}
                )
            }
        }

        // Verify that both articles are displayed
        composeTestRule.onNodeWithText(sampleArticle1.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleArticle2.title).assertIsDisplayed()
    }

    @Test
    fun navigateToDetails_whenArticleIsClicked() {
        var selectedArticle: Article? = null
        val bookmarks = listOf(sampleArticle1, sampleArticle2)

        composeTestRule.setContent {
            FluxNewsTheme {
                BookmarkScreen(
                    state = BookmarkState(articles = bookmarks),
                    navigateToDetails = { selectedArticle = it }
                )
            }
        }

        // Click article and verify navigation
        composeTestRule.onNodeWithText(sampleArticle1.title).performClick()
        assert(selectedArticle == sampleArticle1)
    }
}

