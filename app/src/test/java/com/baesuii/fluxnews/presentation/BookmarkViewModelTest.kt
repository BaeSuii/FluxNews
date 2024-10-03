package com.baesuii.fluxnews.presentation

import com.baesuii.fluxnews.domain.model.Article
import com.google.common.truth.Truth.assertThat
import com.baesuii.fluxnews.domain.use_case.NewsUseCases
import com.baesuii.fluxnews.presentation.bookmark.BookmarkViewModel
import com.baesuii.fluxnews.util.Constants.dummyArticle
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: BookmarkViewModel

    @RelaxedMockK
    private lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = BookmarkViewModel(newsUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getArticles_fetchesAndUpdatesState() = runTest {
        // Given
        val sampleArticles = sampleNews()
        coEvery { newsUseCases.getArticles() } returns flowOf(sampleArticles)

        // When
        viewModel.getArticles()

        // Then
        assertThat(viewModel.state.value.articles).isEqualTo(sampleArticles)
    }


    private fun sampleNews(): List<Article> {
        return listOf(
            dummyArticle.copy(title = "Article 1"),
            dummyArticle.copy(title = "Article 2"),
            dummyArticle.copy(title = "Article 3")
        )
    }
}
